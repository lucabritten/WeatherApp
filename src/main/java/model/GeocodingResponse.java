package model;

import java.util.List;

public record GeocodingResponse(List<GeocodingResult> results) {
}
