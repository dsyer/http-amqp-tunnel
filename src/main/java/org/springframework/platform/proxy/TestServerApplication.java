package org.springframework.platform.proxy;

import org.springframework.boot.SpringApplication;

public class TestServerApplication {

    public static void main(String[] args) {
    	String[] realArgs = new String[args.length + 1];
    	System.arraycopy(args, 0, realArgs, 0,  args.length);
    	realArgs[args.length] = "--spring.profiles.active=test";
        SpringApplication.run(TunnelApplication.class, realArgs);
    }

}
