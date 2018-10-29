package me.notnull.townywebapi.server;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TownyWebServer extends NanoHTTPD {

    public TownyWebServer() throws IOException {
        super(8081);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String[] uri = session.getUri().split("/");
        String page = uri[uri.length-1];
        Map<String, List<String>> parameters = session.getParameters();
        switch(page) {
            case "townList": return newFixedLengthResponse(new TownListRequest().response());
            case "nationList": return newFixedLengthResponse(new NationListRequest().response());
            case "townDetails": return newFixedLengthResponse(new TownDetailsRequest().response(parameters.size() > 0 ? parameters.get("town").get(0) : null));
            case "nationDetails": return newFixedLengthResponse(new NationDetailsRequest().response(parameters.size() > 0 ? parameters.get("nation").get(0) : null));
            case "resDetails": return newFixedLengthResponse(new ResidentDetailsRequest().response(parameters.size() > 0 ? parameters.get("resident").get(0) : null));
        }
        return newFixedLengthResponse("404");
    }
}
