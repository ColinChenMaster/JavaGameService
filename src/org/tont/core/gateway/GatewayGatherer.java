package org.tont.core.gateway;

import java.util.Date;

import org.tont.core.ServerInfoGatherer;

public class GatewayGatherer extends ServerInfoGatherer {
	
	//
	public void handleLogin() {
		handleLoginNum.incrementAndGet();
	}

	@Override
	protected void Log() {
		System.out.println("************************");
		System.out.println(format.format(new Date())
			+ "  ��ǰ���������������ٶ� ��"+getCurrentSpeedPerSecond()+" ������/��\n"
			+ "һ������"+handleLoginNum.get()+"�ε�¼����");
	}
	
	@Override
	protected void reportToGlobal() {
		
	}
}
