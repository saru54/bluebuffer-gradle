package com.core.domain.cq;

import java.util.List;

public record CommentInsertCommand(String content, String userId, String blogId, List<String> images) {
}
