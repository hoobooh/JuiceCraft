package com.usagin.juicecraft.Init;

import com.usagin.juicecraft.projectiles.AlteMinigunProjectile;
import com.usagin.juicecraft.projectiles.AltePanelProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.usagin.juicecraft.JuiceCraft.MODID;

public class ProjectileInit {
    public static final DeferredRegister<EntityType<?>> PROJECTILES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final RegistryObject<EntityType<AlteMinigunProjectile>> ALTE_MINIGUN_PROJECTILE = PROJECTILES.register("alte_minigun_projectile", () -> EntityType.Builder.<AlteMinigunProjectile>of(AlteMinigunProjectile::new, MobCategory.MISC).sized(0.1F, 0.1F).build("alte_minigun_projectile"));
    public static final RegistryObject<EntityType<AltePanelProjectile>> ALTE_PANEL_PROJECTILE = PROJECTILES.register("alte_panel_projectile", () -> EntityType.Builder.<AltePanelProjectile>of(AltePanelProjectile::new, MobCategory.MISC).sized(0.25F, 0.25F).build("alte_panel_projectile"));
}
