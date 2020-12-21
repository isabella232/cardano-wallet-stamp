package com.univocity.envlp.stamp.cardano;

import com.univocity.cardano.wallet.builders.server.*;
import com.univocity.cardano.wallet.embedded.services.*;
import com.univocity.envlp.ui.*;

public class Wallet {

	private final RemoteWalletServer walletServer;
	private EpochDetailsPanel epochDetailsPanel;

	public Wallet(RemoteWalletServer walletServer) {
		this.walletServer = walletServer;

		if (walletServer instanceof EmbeddedWalletServer) {
			EmbeddedWalletServer server = (EmbeddedWalletServer) walletServer;
			cardanoNodeControlPanel = intializeProcess(server.getNodeManager());
			cardanoWalletControlPanel = intializeProcess(server.getWalletManager());
		}
	}

	private ProcessControlPanel intializeProcess(ProcessManager process) {
		ProcessControlPanel out = new ProcessControlPanel(process);
		out.startProcess();
		return out;
	}

	public EpochDetailsPanel getEpochDetailsPanel() {
		if (epochDetailsPanel == null) {
			epochDetailsPanel = new EpochDetailsPanel(walletServer);
		}
		return epochDetailsPanel;
	}

}
