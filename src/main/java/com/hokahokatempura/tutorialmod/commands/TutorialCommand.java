package com.hokahokatempura.tutorialmod.commands;

import java.util.Timer;

import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
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

    // "burning"コマンドが実行されたときの処理
    public static void burningCommand(CommandContext<CommandSourceStack> context) {
        var pos = Vec3Argument.getVec3(context, "中心の座標");
        var source = context.getSource();
        var blockpos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);

        var numX = context.getArgument("X方向の長さ", Integer.class);
        var numY = context.getArgument("Y方向の長さ", Integer.class);
        var numZ = context.getArgument("Z方向の長さ", Integer.class);

        // X方向に-numX ~ numX、Y方向に-numY ~ numY、Z方向に-numZ ~ numZの範囲を燃やす
        for (int x = -numX; x < numX; x++) {
            for (int y = -numY; y < numY; y++) {
                for (int z = -numZ; z < numZ; z++) {
                    var targetPos = blockpos.offset(x, y, z);
                    // 火を発生させようとしている位置が空気だった場合のみ火を設置したい
                    if (source.getLevel().getBlockState(targetPos) == Blocks.AIR.defaultBlockState()) {
                        // また、その位置の下に空気ではないブロックがある場合のみ火を設置したい
                        if (source.getLevel().getBlockState(targetPos.below()) != Blocks.AIR.defaultBlockState()) {
                            // 火を設置する
                            source.getLevel().setBlock(targetPos, Blocks.FIRE.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
    }

    // "randomize"コマンドが実行されたときの処理
    public static void randomizeCommand(CommandContext<CommandSourceStack> context) {
        var pos = Vec3Argument.getVec3(context, "中心の座標");
        var source = context.getSource();
        var blockpos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);

        var numX = context.getArgument("X方向の長さ", Integer.class);
        var numY = context.getArgument("Y方向の長さ", Integer.class);
        var numZ = context.getArgument("Z方向の長さ", Integer.class);

        // X方向に-numX ~ numX、Y方向に-numY ~ numY、Z方向に-numZ ~ numZの範囲のブロックをランダムに書き換える
        for (int x = -numX; x < numX; x++) {
            for (int y = -numY; y < numY; y++) {
                for (int z = -numZ; z < numZ; z++) {
                    var targetPos = blockpos.offset(x, y, z);
                    // 対象のブロックが空気ではなかった場合のみ処理を行う
                    if (source.getLevel().getBlockState(targetPos) != Blocks.AIR.defaultBlockState()) {
                        // ブロックリストからランダムなブロックを取得
                        var randomIndex = (int) (Math.random() * BlockList.blocks.length);
                        var randomBlock = BlockList.blocks[randomIndex];

                        // 対象のブロックをランダムなブロックに書き換える
                        source.getLevel().setBlock(targetPos, randomBlock.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    // メンガーのスポンジを生成する
    private static void generateMengerSponge(BlockPos pos, int depth, Level level) {
        var size = (int) Math.pow(3, depth);
        if (depth == 0) {
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    for (int z = 0; z < size; z++) {
                        // 20%の確率でCRACKED_STONE_BRICKS(ひび割れたブロック)を設置
                        // 20%の確率でMOSSY_STONE_BRICKS(苔むしたブロック)を設置
                        // それ以外はSTONE_BRICKS(普通のブロック)を設置
                        var randomValue = Math.random();
                        if (randomValue > 0.80) {
                            level.setBlock(pos.offset(x, y, z), Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), 3);
                        } else if (randomValue > 0.60) {
                            level.setBlock(pos.offset(x, y, z), Blocks.CRACKED_STONE_BRICKS.defaultBlockState(), 3);
                        } else {
                            level.setBlock(pos.offset(x, y, z), Blocks.STONE_BRICKS.defaultBlockState(), 3);
                        }
                    }
                }
            }
        } else {
            int newSize = size / 3;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    for (int z = 0; z < 3; z++) {
                        if (x == 1 && y == 1 || x == 1 && z == 1 || y == 1 && z == 1) {
                            continue;
                        }
                        generateMengerSponge(pos.offset(x * newSize, y * newSize, z * newSize), depth - 1, level);
                    }
                }
            }
        }
    }

    // 範囲内のブロックをリセットして空気にする処理を行う
    public static void clearTargetVolume(Level level, BlockPos basePos, int size) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                for (int z = 0; z < size; z++) {
                    level.setBlock(basePos.offset(x, y, z), Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }
    }

    private static Timer mengerSpongeTimer = null;
    // "mengersponge"コマンドが実行されたときの処理
    public static void mengerSpongeCommand(CommandContext<CommandSourceStack> context) {
        var source = context.getSource();
        var level = source.getLevel();
        var pos = Vec3Argument.getVec3(context, "中心の座標");
        var blockpos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
        var depth = context.getArgument("繰り返しの深さ", Integer.class);
        var range = (int) Math.pow(3, depth);

        // 事前に範囲内のブロックをリセットして空気にする
        clearTargetVolume(level, blockpos, range);

        // すでにメンガーのスポンジ生成処理が実行中の場合はキャンセルする
        if (mengerSpongeTimer != null) {
            mengerSpongeTimer.cancel();
        }
        // mergeSpongeTimerを使って非同期処理を行う
        mengerSpongeTimer = new Timer();
        mengerSpongeTimer.schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                // メンガーのスポンジを生成
                generateMengerSponge(blockpos, depth, level);
            }
        }, 0);
    }
}
