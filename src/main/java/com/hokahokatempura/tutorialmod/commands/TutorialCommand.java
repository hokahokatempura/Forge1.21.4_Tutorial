package com.hokahokatempura.tutorialmod.commands;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;

public class TutorialCommand {

    // "putblock"コマンドが実行されたときの処理
    public static void putBlockCommand(CommandContext<CommandSourceStack> context) {
        // 引数から座標を取得し、posに格納
        var pos = Vec3Argument.getVec3(context, "ブロックを置く場所");
        var source = context.getSource();
        var blockpos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);

        var numX = context.getArgument("X方向の長さ", Integer.class);
        var numY = context.getArgument("Y方向の長さ", Integer.class);
        var numZ = context.getArgument("Z方向の長さ", Integer.class);

        // X方向にnumX、Y方向にnumY、Z方向にnumZの長さのダイヤモンドブロックを設置
        for (int x = 0; x < numX; x++) {
            for (int y = 0; y < numY; y++) {
                for (int z = 0; z < numZ; z++) {
                    // posの位置にダイヤモンドブロックを設置
                    source.getLevel().setBlock(blockpos.offset(x, y, z), Blocks.DIAMOND_BLOCK.defaultBlockState(), 3);
                }
            }
        }
    }
}
