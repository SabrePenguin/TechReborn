package com.sabrepenguin.techreborn.client.render;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.sabrepenguin.techreborn.Tags;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.*;
import java.util.function.Function;

// TODO: Fix wrong perspective and size

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SideOnly(Side.CLIENT)
public class ModelDynCell implements IModel {
	public static final ModelResourceLocation LOCATION = new ModelResourceLocation(new ResourceLocation(Tags.MODID, "cell"), "inventory");

	public static final IModel MODEL = new ModelDynCell();

	@Nullable
	private static final ResourceLocation defaultEmptyCell = new ResourceLocation(Tags.MODID, "items/cell_base");
	@Nullable
	private static final ResourceLocation emptyLocation = new ResourceLocation(Tags.MODID, "items/cell_cover");
	@Nullable
	private static final ResourceLocation filledLocation = new ResourceLocation(Tags.MODID, "items/cell_fluid");

	private static final float NORTH_Z_FLUID = 7.6f / 16f;
	private static final float SOUTH_Z_FLUID = 8.4f / 16f;


	@Nullable
	private final Fluid fluid;

	public ModelDynCell() {
		this(null);
	}

	public ModelDynCell(@Nullable Fluid fluid) {
		this.fluid = fluid;
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();
		if (fluid == null) {
			if (defaultEmptyCell != null)
				builder.add(defaultEmptyCell);
		} else {
			if (emptyLocation != null)
				builder.add(emptyLocation);
			builder.add(fluid.getStill());
		}
		return builder.build();
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper.getTransforms(state);

		TRSRTransformation transform = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());
		TextureAtlasSprite fluidSprite = null;
		TextureAtlasSprite particleSprite = null;
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

		ResourceLocation baseTexture = fluid == null ? defaultEmptyCell : emptyLocation;

		if(fluid != null) {
			fluidSprite = bakedTextureGetter.apply(fluid.getStill());
		}
		if (baseTexture != null) {
			IBakedModel model = new ItemLayerModel(ImmutableList.of(baseTexture)).bake(state, format, bakedTextureGetter);
			builder.addAll(model.getQuads(null, null, 0));
			particleSprite = model.getParticleTexture();
		}
		if (fluidSprite != null && filledLocation != null) {
			int color = fluid.getColor();
			builder.add(ItemTextureQuadConverter.genQuad(format, transform, 5, 2, 11, 14, NORTH_Z_FLUID,
					fluidSprite, EnumFacing.NORTH, color, -1));
			builder.add(ItemTextureQuadConverter.genQuad(format, transform, 5, 2, 11, 14, SOUTH_Z_FLUID,
					fluidSprite, EnumFacing.SOUTH, color, -1));
			particleSprite = fluidSprite;
		}

		return new ModelDynCell.BakedDynCell(this, builder.build(), particleSprite, format, Maps.immutableEnumMap(transformMap), Maps.newHashMap(), transform.isIdentity());
	}

	@Override
	public ModelDynCell process(ImmutableMap<String, String> customData) {
		String fluidName = customData.get("fluid");
		Fluid fluid = FluidRegistry.getFluid(fluidName);

		if (fluid == null) fluid = this.fluid;

		return new ModelDynCell(fluid);
	}

	@Override
	public ModelDynCell retexture(ImmutableMap<String, String> textures) {
		return new ModelDynCell(fluid);
	}

	public enum LoaderDynCell implements ICustomModelLoader {
		INSTANCE;

		@Override
		public boolean accepts(ResourceLocation modelLocation) {
			return modelLocation.getNamespace().equals(Tags.MODID)
					&& modelLocation.getPath().equals("cell");
//					&& modelLocation.getPath().contains("dynamic_cell");
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) {
			return MODEL;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager) {}
	}

	private static final class BakedDynCellOverrideHandler extends ItemOverrideList {
		public static final ModelDynCell.BakedDynCellOverrideHandler INSTANCE = new ModelDynCell.BakedDynCellOverrideHandler();
		private BakedDynCellOverrideHandler() {
			super(ImmutableList.of());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
			FluidStack fluidStack = FluidUtil.getFluidContained(stack);

			if (fluidStack == null) {
				return originalModel;
			}

			ModelDynCell.BakedDynCell model = (ModelDynCell.BakedDynCell)originalModel;

			Fluid fluid = fluidStack.getFluid();
			String name = fluid.getName();

			if (!model.cache.containsKey(name)) {
				IModel parent = model.parent.process(ImmutableMap.of("fluid", name));
				Function<ResourceLocation, TextureAtlasSprite> textureGetter;
				textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

				IBakedModel bakedModel = parent.bake(new SimpleModelState(model.getTransforms()), model.format, textureGetter);
				model.cache.put(name, bakedModel);
				return bakedModel;
			}

			return model.cache.get(name);
		}
	}

	private static final class BakedDynCell extends BakedItemModel {
		private final ModelDynCell parent;
		private final Map<String, IBakedModel> cache;
		private final VertexFormat format;

		BakedDynCell(ModelDynCell parent, ImmutableList<BakedQuad> quads,
					 TextureAtlasSprite particle, VertexFormat format,
					 ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
					 Map<String, IBakedModel> cache, boolean untransformed) {
			super(quads, particle, transforms, ModelDynCell.BakedDynCellOverrideHandler.INSTANCE, untransformed);
			this.format = format;
			this.parent = parent;
			this.cache = cache;
		}

		public ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms() {
			return this.transforms;
		}
	}

}