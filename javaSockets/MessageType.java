public enum MessageType {
    
    UNICAST,        // send [id] [msg]
    BROADCAST,      // send all [msg]
    CLOSE,          // close
    ASSIGN_ID,      // *Internal, no associated command*
    LIST;           // ls

    public static final MessageType values[] = values();
}
