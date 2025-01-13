package com.hokahokatempura.tutorialmod;

import com.hokahokatempura.tutorialmod.commands.TutorialCommand;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TutorialMod.MODID)
public class TutorialMod {
    public static final String MODID = "tutorialmod";

    public TutorialMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }

    // MOD内でコマンドを登録する場合は、以下のメソッドにコマンド登録処理を記述する
    @SubscribeEvent
    public void onRegisterLocalizeCommand(RegisterCommandsEvent event) {
        {
            // "putblock"コマンドを登録
            LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("putblock")
                    // 引数を設定する ("ブロックを置く場所"という引数をVec3Argument.vec3()で受け取る)
                    .then(Commands.argument("ブロックを置く場所", Vec3Argument.vec3())
                            .then(Commands.argument("X方向の長さ", IntegerArgumentType.integer())
                                    .then(Commands.argument("Y方向の長さ", IntegerArgumentType.integer())
                                            .then(Commands.argument("Z方向の長さ", IntegerArgumentType.integer())
                                                    .executes(context -> {
                                                        // コマンドが実行されると呼び出される処理
                                                        TutorialCommand.putBlockCommand(context);
                                                        return Command.SINGLE_SUCCESS;
                                                    })))));
            event.getDispatcher().register(builder);
        }

        {
            // "burning"コマンドを登録
            LiteralArgumentBuilder<CommandSourceStack> builder2 = Commands.literal("burning")
                    // 引数を設定する ("ブロックを置く場所"という引数をVec3Argument.vec3()で受け取る)
                    .then(Commands.argument("中心の座標", Vec3Argument.vec3())
                            .then(Commands.argument("X方向の長さ", IntegerArgumentType.integer())
                                    .then(Commands.argument("Y方向の長さ", IntegerArgumentType.integer())
                                            .then(Commands.argument("Z方向の長さ", IntegerArgumentType.integer())
                                                    .executes(context -> {
                                                        // コマンドが実行されると呼び出される処理
                                                        TutorialCommand.burningCommand(context);
                                                        return Command.SINGLE_SUCCESS;
                                                    })))));
            event.getDispatcher().register(builder2);
        }

        {
            // "randomize"コマンドを登録
            LiteralArgumentBuilder<CommandSourceStack> builder3 = Commands.literal("randomize")
                    // 引数を設定する ("ブロックを置く場所"という引数をVec3Argument.vec3()で受け取る)
                    .then(Commands.argument("中心の座標", Vec3Argument.vec3())
                            .then(Commands.argument("X方向の長さ", IntegerArgumentType.integer())
                                    .then(Commands.argument("Y方向の長さ", IntegerArgumentType.integer())
                                            .then(Commands.argument("Z方向の長さ", IntegerArgumentType.integer())
                                                    .executes(context -> {
                                                        // コマンドが実行されると呼び出される処理
                                                        TutorialCommand.randomizeCommand(context);
                                                        return Command.SINGLE_SUCCESS;
                                                    })))));
            event.getDispatcher().register(builder3);
        }

        {
            // "mengersponge"コマンドを登録
            LiteralArgumentBuilder<CommandSourceStack> builder4 = Commands.literal("mengersponge")
                    // 引数を設定する ("ブロックを置く場所"という引数をVec3Argument.vec3()で受け取る)
                    .then(Commands.argument("中心の座標", Vec3Argument.vec3())
                            .then(Commands.argument("繰り返しの深さ", IntegerArgumentType.integer())
                                    .executes(context -> {
                                        // コマンドが実行されると呼び出される処理
                                        TutorialCommand.mengerSpongeCommand(context);
                                        return Command.SINGLE_SUCCESS;
                                    })));
            event.getDispatcher().register(builder4);
        }

        {
            // "spiral"コマンドを登録
            LiteralArgumentBuilder<CommandSourceStack> builder5 = Commands.literal("spiral")
                    // 引数を設定する ("ブロックを置く場所"という引数をVec3Argument.vec3()で受け取る)
                    .then(Commands.argument("中心の座標", Vec3Argument.vec3())
                            .then(Commands.argument("Y方向の長さ", IntegerArgumentType.integer())
                                    .then(Commands.argument("回転速度y", FloatArgumentType.floatArg())
                                            .executes(context -> {
                                                // コマンドが実行されると呼び出される処理
                                                TutorialCommand.spiralCommand(context);
                                                return Command.SINGLE_SUCCESS;
                                            }))));
            event.getDispatcher().register(builder5);
        }
    }
}
