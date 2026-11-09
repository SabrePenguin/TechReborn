package com.sabrepenguin.techreborn.capability.stackhandler;

import com.sabrepenguin.techreborn.tileentity.IOManager;
import com.sabrepenguin.techreborn.tileentity.components.SidedConfigComponent;

public interface ISideConfigTE {
	void updateTE();
	SidedConfigComponent<IOManager, SideConfigStackHandler> getSidedConfigComponent();
	IOManager getManager();
}
