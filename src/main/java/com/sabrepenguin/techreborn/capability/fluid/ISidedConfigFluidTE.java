package com.sabrepenguin.techreborn.capability.fluid;

import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import com.sabrepenguin.techreborn.tileentity.IOFluidManager;
import com.sabrepenguin.techreborn.tileentity.components.SidedConfigComponent;

public interface ISidedConfigFluidTE extends ISideConfigTE {
    SidedConfigComponent<IOFluidManager, SideConfigFluidHandler> getFluidConfigComponent();
}
