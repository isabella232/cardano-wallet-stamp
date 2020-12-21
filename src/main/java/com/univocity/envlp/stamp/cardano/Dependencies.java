package com.univocity.envlp.stamp.cardano;

import com.univocity.cardano.wallet.builders.server.*;
import org.springframework.context.annotation.*;

public class Dependencies {

	@Bean
	public WalletConfiguration configuration() {
		return new WalletConfiguration();
	}

	//TODO: refactor to move into CardanoWalletBackendService
	@Bean
	public RemoteWalletServer walletServer() {
		RemoteWalletServer walletServer;
		WalletConfiguration config = configuration();
		int port = config.getCardanoNodePort();
		if (port == -1) {
			walletServer = WalletServer.remote("localhost").connectToPort(config.getWalletServicePort());
		} else {
			EmbeddedWalletServer server = WalletServer.embedded()
					.binariesIn(config.getCardanoToolsDirPath())
					.mainnetNode()
					.configuration(config.getNodeConfigurationFilePath())
					.topology(config.getTopologyFilePath())
					.storeBlockchainIn(config.getBlockchainDirPath())
					.port(config.getCardanoNodePort())
					.ignoreOutput()
					.wallet()
					.enableHttps()
					.port(config.getWalletServicePort())
					.ignoreOutput();

			walletServer = server;

			server.start();
			log.info("Network clock details: " + walletServer.network().clock());
		}
		return walletServer;
	}
}
