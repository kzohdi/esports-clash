package com.spoofy.esportclash.team.domain.model;

public enum Role {
    TOP,
    JUNGLE,
    MIDDLE,
    SUPPORT,
    BOTTOM;

    public static Role fromString(String role) {
        return switch (role) {
            case "TOP" -> Role.TOP;
            case "JUNGLE" -> Role.JUNGLE;
            case "MIDDLE" -> Role.MIDDLE;
            case "SUPPORT" -> Role.SUPPORT;
            case "BOTTOM" -> Role.BOTTOM;
            default -> throw new IllegalStateException("Invalid role: " + role);
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case TOP -> "TOP";
            case JUNGLE -> "JUNGLE";
            case MIDDLE -> "MIDDLE";
            case SUPPORT -> "SUPPORT";
            case BOTTOM -> "BOTTOM";
        };
    }
}
