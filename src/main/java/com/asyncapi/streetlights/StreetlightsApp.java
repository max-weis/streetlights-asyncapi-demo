package com.asyncapi.streetlights;

import com.asyncapi.api.annotations.AsyncAPI;
import com.asyncapi.api.annotations.info.Info;
import com.asyncapi.api.annotations.info.License;
import com.asyncapi.api.annotations.server.Server;
import com.asyncapi.api.annotations.server.ServerVariable;
import com.asyncapi.api.annotations.server.ServerVariables;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 */
@ApplicationPath("/")
@AsyncAPI(
    asyncapi = "2.0.0",
    info = @Info(
        title = "Streetlights API",
        version = "1.0.0",
        description = "The Smartylighting Streetlights API allows you to remotely manage the city lights.\n" + "\n"
            + "    ### Check out its awesome features:\n" + "\n" + "    * Turn a specific streetlight on/off \uD83C\uDF03\n"
            + "    * Dim a specific streetlight \uD83D\uDE0E\n"
            + "    * Receive real-time information about environmental lighting conditions \uD83D\uDCC8",
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(
            name = "production",
            url = "api.streetlights.smartylighting.com:{port}",
            protocol = "mqtt",
            description = "Test broker",
            variables = @ServerVariables(
                name = "port",
                variable = @ServerVariable(
                    description = "Secure connection (TLS) is available through port 8883.",
                    defaultValue = "1883",
                    enumeration = { "1883", "8883" }
                )
            )
        )
    }
)
public class StreetlightsApp extends Application {
}
