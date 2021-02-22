package com.blunix.offlineauth.util;

import java.util.UUID;

import com.google.common.base.Charsets;

public class UUIDUtil {
	
	public static UUID getOfflineUUID(String name) {
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8));
	}
}
