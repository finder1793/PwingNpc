# PwingVisibilityManager
Advanced visibility management system for Citizens NPCs and Players

## Features
- Packet-based NPC visibility control
- Player visibility management system
- Group-based visibility control
- Permission-based access control
- Persistent configuration storage

## Commands

### NPC Visibility
- `/npcvisibility add <id>` - Add yourself to NPC's visible players
- `/npcvisibility remove <id>` - Remove yourself from NPC's visible players
- `/npcvisibility permission <id> <permission>` - Set required permission to see NPC
- `/npcvisibility default <id> <true|false>` - Set default visibility state

### Player Visibility
- `/playerhider hide [player]` - Hide yourself or another player
- `/playerhider show [player]` - Show yourself or another player
- `/playerhider group create <group>` - Create a visibility group
- `/playerhider group add <group> <player>` - Add player to visibility group
- `/playerhider group remove <group> <player>` - Remove player from visibility group
- `/playerhider group list <group>` - List players in visibility group

## Permissions
- `pwingnpc.npc.manage` - Manage NPC visibility
- `pwingnpc.hide.self` - Hide yourself
- `pwingnpc.hide.others` - Hide other players
- `pwingnpc.show.self` - Show yourself
- `pwingnpc.show.others` - Show other players
- `pwingnpc.group.create` - Create visibility groups
- `pwingnpc.group.list` - List visibility groups
- `pwingnpc.group.add` - Add players to groups
- `pwingnpc.group.remove` - Remove players from groups
- `pwingnpc.see.hidden` - See hidden players

## API Usage

// NPC Visibility
NPC npc = CitizensAPI.getNPCRegistry().getById(id);
VisibilityTrait trait = npc.getOrAddTrait(VisibilityTrait.class);
trait.setPermission("custom.permission");
trait.addPlayer(player);

// Player Visibility
PlayerHiderManager manager = plugin.getPlayerHiderManager();
manager.createHiddenGroup("vip");
manager.addPlayerToGroup(player, "vip");
manager.hidePlayer(player);

