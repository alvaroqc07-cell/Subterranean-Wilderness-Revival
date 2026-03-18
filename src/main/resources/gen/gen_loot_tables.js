"use strict";

const fs = require("fs").promises;
const path = require("path");

// Corregido: red_terracota → red_terracotta (typo del original)
// Añadidos: blackstone, basalt (faltaban en el original)
const blocks = [
    "sandstone", "smooth_sandstone", "red_sandstone", "smooth_red_sandstone",
    "terracotta", "white_terracotta", "orange_terracotta", "yellow_terracotta",
    "light_gray_terracotta", "brown_terracotta", "red_terracotta",
    "blackstone", "basalt", "ice"
];

// 1.20.1: el formato de loot tables no cambió, pero se usa "subwild:" en lugar de "expedition:"
async function run()
{
    await fs.mkdir("loot_tables", { recursive: true });

    for(const block of blocks)
    {
        // Coal ore
        await write(`${block}_coal_ore`, {
            type: "minecraft:block",
            pools: [{
                rolls: 1,
                entries: [{
                    type: "minecraft:alternatives",
                    children: [
                        silkTouch(`subwild:${block}_coal_ore`),
                        {
                            type: "minecraft:item",
                            name: "minecraft:coal",
                            functions: [bonusOreDrops(), explosionDecay()]
                        }
                    ]
                }]
            }]
        });

        // Iron ore — drops raw iron in 1.18+
        await write(`${block}_iron_ore`, {
            type: "minecraft:block",
            pools: [{
                rolls: 1,
                entries: [{
                    type: "minecraft:alternatives",
                    children: [
                        silkTouch(`subwild:${block}_iron_ore`),
                        {
                            type: "minecraft:item",
                            name: "minecraft:raw_iron",
                            functions: [bonusOreDrops(), explosionDecay()]
                        }
                    ]
                }]
            }]
        });

        // Gold ore — drops raw gold in 1.18+
        await write(`${block}_gold_ore`, {
            type: "minecraft:block",
            pools: [{
                rolls: 1,
                entries: [{
                    type: "minecraft:alternatives",
                    children: [
                        silkTouch(`subwild:${block}_gold_ore`),
                        {
                            type: "minecraft:item",
                            name: "minecraft:raw_gold",
                            functions: [bonusOreDrops(), explosionDecay()]
                        }
                    ]
                }]
            }]
        });

        // Lapis ore
        await write(`${block}_lapis_ore`, {
            type: "minecraft:block",
            pools: [{
                rolls: 1,
                entries: [{
                    type: "minecraft:alternatives",
                    children: [
                        silkTouch(`subwild:${block}_lapis_ore`),
                        {
                            type: "minecraft:item",
                            name: "minecraft:lapis_lazuli",
                            functions: [
                                { function: "minecraft:set_count", count: { min: 4.0, max: 9.0, type: "minecraft:uniform" } },
                                bonusOreDrops(),
                                explosionDecay()
                            ]
                        }
                    ]
                }]
            }]
        });

        // Redstone ore
        await write(`${block}_redstone_ore`, {
            type: "minecraft:block",
            pools: [{
                rolls: 1,
                entries: [{
                    type: "minecraft:alternatives",
                    children: [
                        silkTouch(`subwild:${block}_redstone_ore`),
                        {
                            type: "minecraft:item",
                            name: "minecraft:redstone",
                            functions: [
                                { function: "minecraft:set_count", count: { min: 4.0, max: 5.0, type: "minecraft:uniform" } },
                                { function: "minecraft:apply_bonus", enchantment: "minecraft:fortune", formula: "minecraft:uniform_bonus_count", parameters: { bonusMultiplier: 1 } },
                                explosionDecay()
                            ]
                        }
                    ]
                }]
            }]
        });

        // Diamond ore
        await write(`${block}_diamond_ore`, {
            type: "minecraft:block",
            pools: [{
                rolls: 1,
                entries: [{
                    type: "minecraft:alternatives",
                    children: [
                        silkTouch(`subwild:${block}_diamond_ore`),
                        {
                            type: "minecraft:item",
                            name: "minecraft:diamond",
                            functions: [bonusOreDrops(), explosionDecay()]
                        }
                    ]
                }]
            }]
        });

        // Emerald ore
        await write(`${block}_emerald_ore`, {
            type: "minecraft:block",
            pools: [{
                rolls: 1,
                entries: [{
                    type: "minecraft:alternatives",
                    children: [
                        silkTouch(`subwild:${block}_emerald_ore`),
                        {
                            type: "minecraft:item",
                            name: "minecraft:emerald",
                            functions: [bonusOreDrops(), explosionDecay()]
                        }
                    ]
                }]
            }]
        });
    }

    console.log("Generated loot tables for " + blocks.length + " block types (" + blocks.length * 7 + " files)");
}

function silkTouch(item) {
    return {
        type: "minecraft:item",
        name: item,
        conditions: [{
            condition: "minecraft:match_tool",
            predicate: {
                enchantments: [{ enchantment: "minecraft:silk_touch", levels: { min: 1 } }]
            }
        }]
    };
}

function bonusOreDrops() {
    return { function: "minecraft:apply_bonus", enchantment: "minecraft:fortune", formula: "minecraft:ore_drops" };
}

function explosionDecay() {
    return { function: "minecraft:explosion_decay" };
}

async function write(name, obj) {
    await fs.writeFile(path.join("loot_tables", `${name}.json`), JSON.stringify(obj, null, "\t"));
}

run();
