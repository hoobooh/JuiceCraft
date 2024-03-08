package com.usagin.juicecraft.friends;

import com.usagin.juicecraft.ai.awareness.SkillManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import java.util.UUID;

public abstract class OldWarFriend extends Friend {
    public static final UUID WEAPONOFWARID = UUID.fromString("7c3f9e32-0fbb-4b93-8230-6a03f2be21b7");
    AttributeModifier WEAPONOFWAR;
    public OldWarFriend(EntityType<? extends FakeWolf> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public boolean isNocturnal(){
        return true;
    }
    public void setSkillLevels(int[] n){
        super.setSkillLevels(n);
        this.setSkillEnabled(this.getSkillEnabled());
    }

    public void setSkillEnabled(boolean[] b) {

        this.skillEnabled = b;
        this.getEntityData().set(FRIEND_SKILLENABLED, SkillManager.makeBooleanHash(b));
        if (this.skillEnabled[0]) {

            this.getAttribute(Attributes.MAX_HEALTH).removePermanentModifier(WEAPONOFWARID);
            this.WEAPONOFWAR = new AttributeModifier(WEAPONOFWARID, "WEAPONOFWAR", 0.2 * this.getSkillLevels()[0], AttributeModifier.Operation.ADDITION);
            this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(this.WEAPONOFWAR);

            this.getAttribute(Attributes.ATTACK_DAMAGE).removePermanentModifier(WEAPONOFWARID);
            this.WEAPONOFWAR = new AttributeModifier(WEAPONOFWARID, "WEAPONOFWAR", 0.1 * this.getSkillLevels()[0], AttributeModifier.Operation.ADDITION);
            this.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(this.WEAPONOFWAR);

            this.setCombatModifier(this.getSkillLevels()[0]);
            this.setHealth(Mth.clamp(this.getHealth(), 0, this.getMaxHealth()));
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).removePermanentModifier(WEAPONOFWARID);
            this.getAttribute(Attributes.ATTACK_DAMAGE).removePermanentModifier(WEAPONOFWARID);
            this.setCombatModifier(this.getCombatModifier() - this.getSkillLevels()[0]);
            this.setHealth(Mth.clamp(this.getHealth(), 0, this.getMaxHealth()));
        }
    }
}
