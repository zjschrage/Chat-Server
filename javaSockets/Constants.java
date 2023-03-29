public class Constants {
    public static final String MESSAGE_FORMAT_ERR = "Message format error";
    public static final String UNKNOWN_USER_ERROR = "User Not Found";
    public static final String HELP_1 = "Type help followed by the command to see its usage";
    public static final String HELP_2 = "Here is a list of accessible commands:";
    public static final String LIST_COMMANDS = "close\nhelp\nls\nsend";
    public static final String HELP_HELP = "FORMAT: help [cmd]\n\nProvides command usage instructions\n\n[cmd] - The help manual for command \"cmd\"";
    public static final String HELP_CLOSE = "FORMAT: close\n\nCloses the connection. Takes no parameters";
    public static final String HELP_LS = "FORMAT: ls\n\nLists all connected users. Takes no parameters";
    public static final String HELP_SEND = "FORMAT: send [all / {id}] [msg]\n\nSends a message\n\n[all] - all will broadcast too all users\n[{id}] - using a specific id will private message tht user\n[msg] - the message to be send";
    public static final String MESSAGE_DELIMITER = ",";
    public static final String COMMAND_DELIMITER = " ";
}
