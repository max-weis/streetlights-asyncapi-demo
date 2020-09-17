package com.asyncapi.streetlights;

import com.asyncapi.api.annotations.AsyncAPI;
import com.asyncapi.api.annotations.info.Info;
import com.asyncapi.api.annotations.info.License;
import com.asyncapi.api.annotations.security.OAuthFlow;
import com.asyncapi.api.annotations.security.OAuthFlows;
import com.asyncapi.api.annotations.security.OAuthScope;
import com.asyncapi.api.annotations.security.SecurityRequirement;
import com.asyncapi.api.annotations.security.SecurityScheme;
import com.asyncapi.api.annotations.security.SecuritySchemes;
import com.asyncapi.api.annotations.security.SecuritySchemeIn;
import com.asyncapi.api.annotations.security.SecuritySchemeType;
import com.asyncapi.api.annotations.server.Server;
import com.asyncapi.api.annotations.server.ServerVariable;
import com.asyncapi.api.annotations.server.ServerVariables;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
@AsyncAPI(
    asyncapi = "2.0.0",
    info = @Info(
        title = "Streetlights API",
        version = "1.0.0",
        description = "The Smartylighting Streetlights API allows you to remotely manage the city lights.\n" + "\n"
            + "    ### Check out its awesome features:\n" + "\n" + "    * TurnOnOff a specific streetlight on/off \uD83C\uDF03\n"
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
            ),
            security = {
                @SecurityRequirement(
                    name = "apiKey"
                ),
                @SecurityRequirement(
                    name = "supportedOauthFlows",
                    values = { "streetlights:on", "streetlights:off", "streetlights:dim" }
                ),
                @SecurityRequirement(
                    name = "openIdConnectWellKnown"
                )
            }
        )
    }
)
@SecuritySchemes(
    values = {
        @SecurityScheme(
            name = "apiKey",
            type = SecuritySchemeType.APIKEY,
            in = SecuritySchemeIn.USER,
            description = "Provide your API key as the user and leave the password empty."
        ),
        @SecurityScheme(
            name = "supportedOauthFlows",
            type = SecuritySchemeType.OAUTH2,
            description = "Flows to support OAuth 2.0",
            flows = @OAuthFlows(
                implicit = @OAuthFlow(
                    authorizationUrl = "https://authserver.example/auth",
                    scopes = {
                        @OAuthScope(
                            name = "streetlights:on",
                            description = "Ability to switch lights on"
                        ),
                        @OAuthScope(
                            name = "streetlights:off",
                            description = "Ability to switch lights off"
                        ),
                        @OAuthScope(
                            name = "streetlights:dim",
                            description = "Ability to dim the lights"
                        )
                    }
                ),
                password = @OAuthFlow(
                    tokenUrl = "https://authserver.example/token",
                    scopes = {
                        @OAuthScope(
                            name = "streetlights:on",
                            description = "Ability to switch lights on"
                        ),
                        @OAuthScope(
                            name = "streetlights:off",
                            description = "Ability to switch lights off"
                        ),
                        @OAuthScope(
                            name = "streetlights:dim",
                            description = "Ability to dim the lights"
                        )
                    }
                ),
                clientCredentials = @OAuthFlow(
                    tokenUrl = "https://authserver.example/token",
                    scopes = {
                        @OAuthScope(
                            name = "streetlights:on",
                            description = "Ability to switch lights on"
                        ),
                        @OAuthScope(
                            name = "streetlights:off",
                            description = "Ability to switch lights off"
                        ),
                        @OAuthScope(
                            name = "streetlights:dim",
                            description = "Ability to dim the lights"
                        )
                    }
                ),
                authorizationCode = @OAuthFlow(
                    authorizationUrl = "https://authserver.example/auth",
                    tokenUrl = "https://authserver.example/token",
                    refreshUrl = "https://authserver.example/refresh",
                    scopes = {
                        @OAuthScope(
                            name = "streetlights:on",
                            description = "Ability to switch lights on"
                        ),
                        @OAuthScope(
                            name = "streetlights:off",
                            description = "Ability to switch lights off"
                        ),
                        @OAuthScope(
                            name = "streetlights:dim",
                            description = "Ability to dim the lights"
                        )
                    }
                )
            )
        ),
        @SecurityScheme(
            name = "openIdConnectWellKnown",
            type = SecuritySchemeType.OPENIDCONNECT,
            openIdConnectUrl = "https://authserver.example/.well-known"
        )
    }
)
public class StreetlightsApp extends Application {
}
