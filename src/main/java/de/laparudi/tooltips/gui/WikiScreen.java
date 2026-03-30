package de.laparudi.tooltips.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WikiScreen extends Screen {

    private interface WikiElement {
        int render(GuiGraphics g, Font font, int x, int y, int maxWidth);
    }

    private record TextElement(String text, int color) implements WikiElement {
        @Override
        public int render(GuiGraphics g, Font font, int x, int y, int maxWidth) {
            List<FormattedCharSequence> lines = font.split(Component.literal(text), maxWidth);
            int currentY = y;
            for (FormattedCharSequence line : lines) {
                g.drawString(font, line, x, currentY, color, false);
                currentY += 10;
            }
            return currentY + 5;
        }
    }

    private record ItemElement(ItemStack stack, String label) implements WikiElement {
        @Override
        public int render(GuiGraphics g, Font font, int x, int y, int maxWidth) {
            g.renderFakeItem(stack, x, y);
            g.drawString(font, label, x + 22, y + 4, 0xFFAAAAAA, false);
            return y + 25;
        }
    }

    private record ImageElement(ResourceLocation texture, int w, int h) implements WikiElement {
        @Override
        public int render(GuiGraphics g, Font font, int x, int y, int maxWidth) {
            g.blit(texture, x, y, 0, 0, w, h, w, h);
            return y + h + 10;
        }
    }

    private static class WikiPage {
        String title;
        String description;
        ItemStack icon;
        List<WikiElement> elements = new ArrayList<>();

        public WikiPage(String title, String description, ItemStack icon) {
            this.title = title;
            this.description = description;
            this.icon = icon;
        }

        public WikiPage addText(String text) { this.elements.add(new TextElement(text, 0xFFDDDDDD)); return this; }
        public WikiPage addItem(ItemStack stack, String desc) { this.elements.add(new ItemElement(stack, desc)); return this; }
        public WikiPage addImage(String path, int w, int h) {
            this.elements.add(new ImageElement(ResourceLocation.fromNamespaceAndPath("cytooxien-tooltips", "wiki/" + path), w, h));
            return this;
        }
    }

    private static final List<WikiPage> PAGES = new ArrayList<>();
    static {
        WikiPage tutorialPage = new WikiPage("Willkommen", "Erste Schritte", new ItemStack(Items.BOOK));
        tutorialPage.addText("Dies ist eine Wiki-Seite mit einer GUI-Vorschau:")
                .addImage("generic_54.png", 176, 125)
                .addText("§7Hier siehst du die Item-Liste:")
                .addItem(new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), "§6Netherite Upgrade");

        PAGES.add(tutorialPage);
    }

    private final int sidebarWidth = 110;
    private EditBox searchBox;
    private Button backButton;
    private WikiPage selectedPage = null;
    private final List<WikiPage> filteredPages = new ArrayList<>();

    public WikiScreen() { super(Component.literal("Wiki")); }

    @Override
    protected void init() {
        this.clearWidgets();
        int contentX = sidebarWidth + 15;

        this.addRenderableWidget(Button.builder(Component.literal("Home"), b -> { selectedPage = null; updateFilter(); }).bounds(5, 40, sidebarWidth - 10, 20).build());

        this.backButton = Button.builder(Component.literal("Zurück"), b -> selectedPage = null).bounds(contentX, 15, 60, 18).build();
        this.addRenderableWidget(this.backButton);

        this.searchBox = new EditBox(this.font, contentX + 65, 15, this.width - contentX - 85, 18, Component.literal("Suche..."));
        this.searchBox.setResponder(s -> { if(!s.isEmpty()) selectedPage = null; updateFilter(); });
        this.addRenderableWidget(this.searchBox);
        updateFilter();
    }

    private void updateFilter() {
        filteredPages.clear();
        String query = searchBox.getValue().toLowerCase(Locale.ROOT);
        for (WikiPage p : PAGES) {
            if (p.title.toLowerCase(Locale.ROOT).contains(query) || p.description.toLowerCase(Locale.ROOT).contains(query)) {
                filteredPages.add(p);
            }
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean bl) {
        if (selectedPage == null) {
            int x = sidebarWidth + 15; int y = 45;
            for (WikiPage p : filteredPages) {
                if (event.x() >= x && event.x() <= this.width - 20 && event.y() >= y && event.y() <= y + 35) {
                    this.selectedPage = p;
                    return true;
                }
                y += 40;
            }
        }
        return super.mouseClicked(event, bl);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(g);
        if (backButton != null) backButton.visible = (selectedPage != null);
        if (searchBox != null) searchBox.visible = (selectedPage == null);

        // Sidebar
        g.fill(0, 0, sidebarWidth, this.height, 0xCC121212);
        g.fill(sidebarWidth, 0, sidebarWidth + 1, this.height, 0xFF555555);

        if (selectedPage == null) renderList(g, sidebarWidth + 15, mouseX, mouseY);
        else renderPage(g, sidebarWidth + 15);
        super.render(g, mouseX, mouseY, partialTick);
    }

    private void renderList(GuiGraphics g, int x, int mx, int my) {
        int y = 45;
        for (WikiPage p : filteredPages) {
            boolean hover = mx >= x && mx <= this.width - 20 && my >= y && my <= y + 35;
            g.fill(x, y, this.width - 20, y + 35, hover ? 0x44FFFFFF : 0x22FFFFFF);

            g.renderFakeItem(p.icon, x + 5, y + 10);

            // TITLE
            g.drawString(this.font, p.title, x + 30, y + 5, 0xFFFFFFFF, true);
            String shortDesc = p.description.length() > 40 ? p.description.substring(0, 37) + "..." : p.description;
            g.drawString(this.font, "§7" + shortDesc, x + 30, y + 18, 0xAAAAAA, false);

            y += 40;
        }
    }

    private void renderPage(GuiGraphics g, int x) {
        int headerY = 45;
        g.renderFakeItem(selectedPage.icon, x, headerY);
        g.drawString(this.font, "§l" + selectedPage.title, x + 25, headerY + 4, 0xFFAAFF00, true);
        g.fill(x, headerY + 22, this.width - 20, headerY + 23, 0x44FFFFFF);

        int currentY = headerY + 35;
        int maxWidth = this.width - x - 30;
        for (WikiElement element : selectedPage.elements) {
            currentY = element.render(g, this.font, x, currentY, maxWidth);
            if (currentY > this.height) break;
        }
    }

    @Override
    public boolean isPauseScreen() { return false; }
}