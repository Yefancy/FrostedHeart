package com.teammoeg.frostedheart.trade;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class ExtendPolicyGroup extends PolicyGroup {
	ResourceLocation ref;
	public ExtendPolicyGroup(List<PolicyCondition> conditions, ResourceLocation ref) {
		super(conditions);
		this.ref = ref;
	}
	public ExtendPolicyGroup(JsonObject jo) {
		super(jo);
		ref=new ResourceLocation(jo.get("parent").getAsString());
	}
	public ExtendPolicyGroup(PacketBuffer pb) {
		super(pb);
		ref=pb.readResourceLocation();
	}
	@Override
	public void tickNoCheck(int deltaDay, Map<String, Float> data, VillagerEntity ve) {
		TradePolicy.policies.get(ref).tick(deltaDay, data,ve);
	}
	@Override
	public void fetchNoCheck(List<BuyData> buys, List<SellData> sell, Map<String, Float> data, VillagerEntity ve) {
		TradePolicy.policies.get(ref).fetch(buys, sell, data, ve);
	}
	@Override
	public JsonElement serialize() {
		JsonObject jo=super.serialize().getAsJsonObject();
		jo.addProperty("parent",ref.toString());
		return jo;
	}
	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeBoolean(true);
		super.write(buffer);
		buffer.writeResourceLocation(ref);
	}
}
