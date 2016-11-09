package com.hazeluff.discord.canucksbot.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
	private static final DateTimeFormatter NHL_API_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

	public static LocalDateTime parseNHLDate(String date) {
		try {
			return LocalDateTime.parse(date.replaceAll("Z", "+0000"), NHL_API_FORMAT);
		} catch (DateTimeParseException e) {
			LOGGER.error("Could not parse date [" + date + "]", e);
			return null;
		}
	}

	/**
	 * Finds the difference (in ms) between two LocalDateTime. Returns date2 - date1.
	 * 
	 * @param date1
	 * @param date2
	 * @return time difference in ms
	 */
	public static long diffMs(LocalDateTime date1, LocalDateTime date2) {
		return Duration.between(date1, date2).getSeconds() * 1000;
	}
}
