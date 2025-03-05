package dasturlash.topnews.dto;

public record StandardResponse(
        String message,
        boolean status,
        Object object
) {
}
