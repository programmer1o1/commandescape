package hi.sierra.commandescape.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
//? if <1.19.3 {
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.network.message.LastSeenMessageList;
import java.time.Instant;
import java.util.Optional;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {

    @Shadow
    protected TextFieldWidget chatField;

    @Inject(
            method = "keyPressed",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode == 257 && this.chatField != null) { // Enter key
            String text = this.chatField.getText();
            if (text.startsWith("\\")) {
                // Remove backslash
                String message = text.substring(1);

                MinecraftClient client = MinecraftClient.getInstance();
                if (client.player != null && client.getNetworkHandler() != null) {
                    //? if >=1.19.3 {
                    /*client.getNetworkHandler().sendChatMessage(message);
                    *///?} else {

                    // Create unsigned message packet
                    ChatMessageC2SPacket packet = new ChatMessageC2SPacket(
                            message,
                            Instant.now(),
                            0L,
                            MessageSignatureData.EMPTY,
                            false,
                            new LastSeenMessageList.Acknowledgment(LastSeenMessageList.EMPTY, Optional.empty())
                    );

                    client.getNetworkHandler().sendPacket(packet);
                    //?}
                }

                // Close chat screen
                client.setScreen(null);

                cir.setReturnValue(true);
            }
        }
    }
}