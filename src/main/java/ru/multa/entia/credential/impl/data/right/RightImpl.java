package ru.multa.entia.credential.impl.data.right;

import org.bson.types.ObjectId;
import ru.multa.entia.credential.api.data.right.Right;

public record RightImpl(ObjectId id, String value) implements Right {}
