package com.teammoeg.frostedheart.research.clues;

import java.util.List;

import com.google.gson.JsonObject;

import dev.ftb.mods.ftbteams.data.Team;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class AdvancementClue extends TickListenerClue {
	ResourceLocation advancement = new ResourceLocation("minecraft:story/root");
	String criterion="";
	public AdvancementClue(String name, List<String> desc, float contribution) {
		super(name, desc, contribution);
	}

	public AdvancementClue(String name, float contribution) {
		super(name, contribution);
	}

	public AdvancementClue(JsonObject jo) {
		super(jo);
		advancement=new ResourceLocation(jo.get("advancement").getAsString());
		if(jo.has("criterion"))
		criterion=jo.get("criterion").getAsString();
	}

	public AdvancementClue(PacketBuffer pb) {
		super(pb);
		advancement=pb.readResourceLocation();
		criterion=pb.readString();
	}

	@Override
	public boolean isCompleted(Team t, ServerPlayerEntity player) {
		Advancement a = player.server.getAdvancementManager().getAdvancement(advancement);
		if (a == null) {
			return false;
		}

		AdvancementProgress progress = player.getAdvancements().getProgress(a);

		if (criterion.isEmpty()) {
			return progress.isDone();
		}
		CriterionProgress criterionProgress = progress.getCriterionProgress(criterion);
		return criterionProgress != null && criterionProgress.isObtained();
	}

	@Override
	public String getType() {
		return "advancement";
	}

	@Override
	public JsonObject serialize() {
		JsonObject jo= super.serialize();
		jo.addProperty("advancement",advancement.toString());
		if(!criterion.isEmpty())
			jo.addProperty("criterion", criterion);
		return jo;
	}

	@Override
	public void write(PacketBuffer buffer) {
		super.write(buffer);
		buffer.writeResourceLocation(advancement);
		buffer.writeString(criterion);
	}

}