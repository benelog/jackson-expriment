package net.benelog.jackson;

import static org.assertj.core.api.BDDAssertions.*;

import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class JsonCreatorTest {
	@Test
	void parse() throws JsonProcessingException {
		var objectMapper = new ObjectMapper()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.registerModule(new JavaTimeModule());

		var json = """
			{
			"accessDateTime": "2019-10-10T11:14:16Z",
			"ip": "175.242.91.54",
			"username": "benelog"
			}
			""";

		AccessLog accessLog = objectMapper.readValue(json, AccessLog.class);

		then(accessLog.getAccessDateTime()).isEqualTo("2019-10-10T11:14:16Z");
		then(accessLog.getIp()).isEqualTo("175.242.91.54");
		then(accessLog.getUsername()).isEqualTo("benelog");
	}

	public static class AccessLog {
		private final Instant accessDateTime;
		private final String ip;
		private final String username;

		@JsonCreator
		public AccessLog(
			@JsonProperty("accessDateTime") Instant accessDateTime,
			@JsonProperty("ip") String ip,
			@JsonProperty("username") String username) {
			this.accessDateTime = accessDateTime;
			this.ip = ip;
			this.username = username;
		}

		public Instant getAccessDateTime() {
			return accessDateTime;
		}

		public String getIp() {
			return ip;
		}

		public String getUsername() {
			return username;
		}

		@Override
		public String toString() {
			return "AccessLog{" +
				"accessDateTime=" + accessDateTime +
				", ip='" + ip + '\'' +
				", username='" + username + '\'' +
				'}';
		}
	}
}
