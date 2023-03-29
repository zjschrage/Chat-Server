public enum Command {
    
    ls,      // ls
    send,    // send [all / {id}] [msg]
    help,    // help
    close;   // close

    public static final Command values[] = values();
}
