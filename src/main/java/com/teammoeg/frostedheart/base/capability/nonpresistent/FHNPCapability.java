package com.teammoeg.frostedheart.base.capability.nonpresistent;

import com.teammoeg.frostedheart.base.capability.IFHCapability;
import com.teammoeg.frostedheart.mixin.forge.CapabilityManagerAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

public class FHNPCapability<C> implements IFHCapability{
	private Class<C> capClass;
	private Capability<C> capability;
	private NonNullSupplier<C> factory;

	public FHNPCapability(Class<C> capClass, NonNullSupplier<C> factory) {
		super();
		this.capClass = capClass;
		this.factory = factory;
	}
	@SuppressWarnings("unchecked")
	public void register() {
        CapabilityManager.INSTANCE.register(capClass, new Capability.IStorage<C>() {
            public void readNBT(Capability<C> capability, C instance, Direction side, Tag nbt) {
            }

            public Tag writeNBT(Capability<C> capability, C instance, Direction side) {
                return new CompoundTag();
            }
        }, ()->factory.get());
        capability=(Capability<C>) ((CapabilityManagerAccess)(Object)CapabilityManager.INSTANCE).getProviders().get(capClass.getName().intern());
	}
	public ICapabilityProvider provider() {
		return new FHNPCapabilityProvider<>(this);
	}
	LazyOptional<C> createCapability(){
		return LazyOptional.of(factory);
	}
	public LazyOptional<C> getCapability(Object cap) {
		if(cap instanceof ICapabilityProvider)
			return ((ICapabilityProvider)cap).getCapability(capability);
		return LazyOptional.empty();
	}
    public Capability<C> capability() {
		return capability;
	}
}
