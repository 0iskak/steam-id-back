package steam.id.back;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.net.URL;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUtil {
    static ObjectMapper MAPPER = new ObjectMapper();
    static ObjectWriter WRITER = MAPPER.writerWithDefaultPrettyPrinter();
    // SET YOUR API
    static String API = "A04A35FE065295A1E0ECF87D9B2BB605";
    static String GET_ID = String.format("https://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/?key=%s&vanityurl=", API);
    static String GET_SMRY = String.format("https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=%s&steamids=", API);
    static String GET_BANS = String.format("https://api.steampowered.com/ISteamUser/GetPlayerBans/v1/?key=%s&steamids=", API);

    static JsonNode node;

    @SneakyThrows
    public static String getUser(String param) {
        User user = new User();
        String json;

        if (param.length() != 17) {
            json = new String(new URL(GET_ID+param).openStream().readAllBytes());
            param = toString(MAPPER.readValue(json, ObjectNode.class).get("response").get("steamid"));
        }

        long id = Long.parseLong(param);
        user.setSteamId64(id);
        user.setSteamId32(id - 76561197960265728L);
        user.setSteamId3("U:1:"+user.getSteamId32());
        user.setSteamId(String.format("STEAM_0:%d:%d", user.getSteamId32()%2, user.getSteamId32()/2));

        json = new String(new URL(GET_SMRY +id).openStream().readAllBytes());
        node = MAPPER.readValue(json, ObjectNode.class).get("response").get("players").get(0);

        user.setImage(get("avatarfull"));
        user.setName(get("realname"));
	String timeCreated = get("timecreated");
	if (timeCreated == null)
		timeCreated = "0";
        user.setCreated(Long.parseLong(timeCreated));
        user.setStatus(
                switch (Integer.parseInt(get("personastate"))) {
                    case 1 -> "Online";
                    case 2 -> "Busy";
                    case 3 -> "Away";
                    case 4 -> "Snooze";
                    case 5 -> "Looking to trade";
                    case 6 -> "Looking to play";
                    default -> "Offline";
                }
        );
        user.setVisibility(get("communityvisibilitystate").equals("3") ? "Public" : "Private");
        user.setNick(get("personaname"));
        user.setProfileUrl(get("profileurl"));
        user.setPermanentUrl("https://steamcommunity.com/profiles/"+user.getSteamId64()+"/");

        json = new String(new URL(GET_BANS+id).openStream().readAllBytes());
        node = MAPPER.readValue(json, ObjectNode.class).get("players").get(0);

        user.setGamesBan(Integer.parseInt(get("NumberOfGameBans")));
        user.setCommunityBan(Boolean.parseBoolean(get("CommunityBanned")));
        user.setVacBan(!get("NumberOfVACBans").equals("0"));
        user.setTradeBan(!get("EconomyBan").equals("none"));




        return user.toString();
    }

    private static String get(String key) {
        return toString(node.get(key));
    }

    private static String toString(JsonNode node) {
        return node == null ? null : node.toString().replace("\"", "");
    }

    @SneakyThrows
    public static String json(User object) {
       return "<div style=\"white-space: pre-wrap\">"+WRITER.writeValueAsString(object)+"</div>";
    }
}
