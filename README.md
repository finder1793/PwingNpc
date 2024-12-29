# PwingNpc
Robust Packet NPC plugin with No BS

## Features
- Lightweight packet-based NPCs
- Dynamic trait system
- Persistent storage
- Simple command interface
- API for developers

## Commands
- `/npc create <name>` - Create a new NPC
- `/npc remove <id>` - Remove an NPC
- `/npc trait <id> <trait> <action> [args]` - Manage NPC traits
- `/npc move <id>` - Move NPC to your location

## Traits
- Movement - Controls NPC movement and pathfinding
- Skin - Manages NPC skin appearance
- LookClose - Makes NPCs look at nearby players

## API Usage

NPCManager npcManager = plugin.getNPCManager();
NPC npc = npcManager.createNPC("MyNPC", location);
npc.addTrait(new MovementTrait());
npc.spawn();

