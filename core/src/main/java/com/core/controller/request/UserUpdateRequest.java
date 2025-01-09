package com.core.controller.request;

public record UserUpdateRequest(String id,String name,String password,String email,String phone,String description,String image) {
}
