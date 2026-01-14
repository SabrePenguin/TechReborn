package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.util.MetadataHelper;

import java.util.Collection;
import java.util.List;

public interface IMetaBlock {
    Collection<MetadataHelper> getMetadata();
    List<MetadataHelper> getListMetadata();
}
