package com.usagin.juicecraft.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.usagin.juicecraft.friends.Friend;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import org.jetbrains.annotations.NotNull;

public abstract class FriendEntityModel<T extends Friend> extends HierarchicalModel<T> implements ArmedModel {
    public abstract void translateToBack(@NotNull PoseStack pPoseStack);
}
