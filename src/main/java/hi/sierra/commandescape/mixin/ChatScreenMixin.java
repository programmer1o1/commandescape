package hi.sierra.commandescape.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
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
                    client.getNetworkHandler().sendChatMessage(message);
                }

                // Close chat screen
                client.setScreen(null);

                cir.setReturnValue(true);
            }
        }
    }
}