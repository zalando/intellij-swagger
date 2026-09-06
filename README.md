[![Build Plugin](https://github.com/zalando/intellij-swagger/actions/workflows/build.yml/badge.svg)](https://github.com/zalando/intellij-swagger/actions/workflows/build.yml)

# Zalando OpenAPI Editor
Zalando OpenAPI Editor makes it easy to edit OpenAPI and Swagger specification files inside IntelliJ IDEA. You can find it on JetBrains' [plugin page](https://plugins.jetbrains.com/plugin/8347).

![Zalando OpenAPI Editor features](https://github.com/zalando/intellij-swagger/blob/master/docs/features.gif?raw=true)

## Usage

Open an OpenAPI or Swagger specification file, that's it.

## Custom Extensions

You can extend the auto completion in order to provide custom keys and and values. The plugin provides the following extension points for this:

```xml
<extensionPoints>
    <extensionPoint qualifiedName="org.zalando.intellij.swagger.customFieldFactory" interface="org.zalando.intellij.swagger.extensions.completion.swagger.SwaggerCustomFieldCompletionFactory"/>
    <extensionPoint qualifiedName="org.zalando.intellij.swagger.customValueFactory" interface="org.zalando.intellij.swagger.extensions.completion.swagger.SwaggerCustomValueCompletionFactory"/>
</extensionPoints>
```

See the [Zalando extension example](https://github.com/zalando/intellij-swagger/blob/master/examples/extensions-zalando) for details.

## Development

Developing the Zalando OpenAPI Editor is easy, just execute the following command:

```./gradlew runIde```

This will start IntelliJ IDEA with the plugin installed.

## How to Contribute
To contribute, please fork and create a pull request.

### Bug Fixes
If you find a bug, it would be awesome if you created an issue about it. Please include a clear description of the problem so that we can fix it!

## License
The MIT License (MIT) Copyright © 2017 Zalando SE, https://tech.zalando.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


## 🌐 Web Resources & Interactive Index
- [THOR MERGE](https://learnquesters.pages.dev/thor-merge.html)
- [BRICK MATCH](https://studyplayings.web.app/brick-match.html)
- [SPRUNKI CHARACTER MAKER OC](https://studyplayings.web.app/sprunki-character-maker-oc.html)
- [ULTIMATE FLYING CAR 2](https://themindzone.pages.dev/ultimate-flying-car-2.html)
- [MAKE TWO](https://studyplayings.web.app/make-two.html)
- [THE WHITE ROOM 5](https://learnquester.github.io/the-white-room-5.html)
- [CATEGORY TANK](https://studyplayings.web.app/category-tank.html)
- [FORMULA TRAFFIC RACER](https://thelearnquesters.pages.dev/formula-traffic-racer.html)
- [RAMP CAR JUMPING](https://learnquester.pages.dev/ramp-car-jumping.html)
- [DREAM ROOM MAKEOVER](https://studyplayings.pages.dev/dream-room-makeover.html)
- [PARTY ANIMALS CATS EVOLUTION](https://learnquester.pages.dev/party-animals-cats-evolution.html)
- [ASMR BEAUTY CLINIC](https://learnquester.pages.dev/asmr-beauty-clinic.html)
- [BOUNCE DUNK BASKETBALL](https://learnquester.pages.dev/bounce-dunk-basketball.html)
- [CATEGORY BIKE 2](https://studyplayings.web.app/category-bike-2.html)
- [LOVIE CHICS SPRING BREAK FASHION](https://thelearnquesters.pages.dev/lovie-chics-spring-break-fashion.html)
- [WOOLLOOP COLOR PUZZLE](https://studyplaying.github.io/woolloop-color-puzzle.html)
- [AGENT SQUAD](https://thelearnquesters.pages.dev/agent-squad.html)
- [STICKMAN ARCHERO FIGHT STICK SHADOW FIGHT WAR](https://studyplayings.web.app/stickman-archero-fight-stick-shadow-fight-war.html)
- [TERMS](https://studyplayings.web.app/terms.html)
- [CATEGORY 2D1 070](https://studyplayings.web.app/category-2d1-070.html)
- [CATEGORY PUZZLE 9](https://thelearnquesters.pages.dev/category-puzzle-9.html)
- [CATEGORY SNIPER39](https://studyplayings.web.app/category-sniper39.html)
- [INDEX17](https://studyplayings.web.app/index17.html)
- [CATEGORY CONTROLLER](https://studyplayings.pages.dev/category-controller.html)
- [CATEGORY 2D1 060](https://studyplayings.pages.dev/category-2d1-060.html)
- [UNDERWATER SURVIVAL](https://studyplaying.github.io/underwater-survival.html)
- [BARREL ROLLER AMAZING RUNNER](https://learnquester.pages.dev/barrel-roller-amazing-runner.html)
- [TOY RUMBLE 3D](https://learnquester.pages.dev/toy-rumble-3d.html)
- [SORT GAME TOY SORT](https://thelearnquesters.pages.dev/sort-game-toy-sort.html)
- [MY CASTLE MERGE STORY](https://studyplayings.web.app/my-castle-merge-story.html)
- [TAIL GUN CHARLIE](https://studyplayings.web.app/tail-gun-charlie.html)
- [ARCHER DUNGEON HERO](https://studyplaying.github.io/archer-dungeon-hero.html)
- [WORLD WAR BROTHERS WW2](https://studyplayings.web.app/world-war-brothers-ww2.html)
- [IDLE PINBALL MERGE CLICKER](https://studyplayings.web.app/idle-pinball-merge-clicker.html)
- [CATEGORY BIKE](https://studyplayings.pages.dev/category-bike.html)
- [MINI GRAND THEFT CITY](https://studyplayings.web.app/mini-grand-theft-city.html)
- [CATEGORY CASUAL](https://studyplayings.pages.dev/category-casual.html)
- [NOOB JAILBREAK 2](https://studyplayings.web.app/noob-jailbreak-2.html)
- [WORLD SOCCER](https://studyplayings.web.app/world-soccer.html)
- [GROW A GARDEN ONLINE OFFLINE](https://studyplaying.github.io/grow-a-garden-online-offline.html)
- [BATTLEDUDES IO](https://studyplayings.web.app/battledudes-io.html)
- [CLAY CRAFT TYCOON](https://learnquester.pages.dev/clay-craft-tycoon.html)
- [CATEGORY CASUAL](https://learnquester.pages.dev/category-casual.html)
- [INDEX13](https://studyplayings.pages.dev/index13.html)
- [SNAKEMAXX](https://studyplaying.github.io/snakemaxx.html)
- [SPIDER SOLITAIRE](https://thelearnquesters.pages.dev/spider-solitaire.html)
- [CATEGORY HORROR90](https://learnquester.pages.dev/category-horror90.html)
- [CATEGORY SIMULATION](https://studyplayings.web.app/category-simulation.html)
- [MONSTER DASH](https://studyplaying.github.io/monster-dash.html)
- [PUSHIO](https://studyplaying.github.io/pushio.html)
- [CATEGORY WAR137](https://thequizzone.pages.dev/category-war137.html)
- [CATEGORY HERO72](https://learnquester.pages.dev/category-hero72.html)
- [STEAL ITEMS IO](https://studyplayings.pages.dev/steal-items-io.html)
- [TAIL GUN CHARLIE](https://thelearnquesters.pages.dev/tail-gun-charlie.html)
- [DRIVE IN CINEMA IDLE GAME](https://learnquester.pages.dev/drive-in-cinema-idle-game.html)
- [CATEGORY FASHION105](https://theskillquest.pages.dev/category-fashion105.html)
- [MATH RUNNER](https://theskillquest.pages.dev/math-runner.html)
- [DARLING DOLL](https://iskillquest.pages.dev/darling-doll.html)
- [DOGGO JUMP](https://theskillquest.pages.dev/doggo-jump.html)
- [GEOMETRY LITE](https://themindplays.pages.dev/geometry-lite.html)
- [CATEGORY MAHJONG 2](https://theskillquest.pages.dev/category-mahjong-2.html)
- [CATEGORY MERGE224](https://theskillquest.pages.dev/category-merge224.html)
- [BUBBLE SHOOTER WONDERS OF EGYPT](https://studyplayings.pages.dev/bubble-shooter-wonders-of-egypt.html)
- [CATEGORY TRAFFIC34](https://themindskillplayplay.pages.dev/category-traffic34.html)
- [BLOCK PIXEL GUN APOCALYPSE 3](https://thelearnquesters.pages.dev/block-pixel-gun-apocalypse-3.html)
- [GLOVES OF BLOCK](https://thelearnquesters.pages.dev/gloves-of-block.html)
- [BLOONS SURVIVALIO](https://thelearnquesters.pages.dev/bloons-survivalio.html)
- [FOOD TRUCK CHEF COOKING](https://studyplaying.github.io/food-truck-chef-cooking.html)
- [CATEGORY POOL17](https://studyplayings.web.app/category-pool17.html)
- [STICK WAR SAGA](https://studyplayings.web.app/stick-war-saga.html)
- [CATEGORY ANIMAL](https://studyplayings.pages.dev/category-animal.html)
- [CATEGORY BATTLE](https://studyplayings.pages.dev/category-battle.html)
- [BUS DRIVER](https://iskillplay.web.app/bus-driver.html)
- [ROBOT RUNNER FIGHT](https://iskillplay.web.app/robot-runner-fight.html)
- [HALLOWEEN STICKMAN](https://iskillplay.web.app/halloween-stickman.html)
- [INDEX15](https://iskillquest.pages.dev/index15.html)
- [SAMURAI LEGACY](https://iskillplay.web.app/samurai-legacy.html)
- [TWO DOTS REMASTERED](https://iskillplay.web.app/two-dots-remastered.html)
- [POXEL IO](https://iskillplay.web.app/poxel-io.html)
- [INDEX3](https://studyplayings.web.app/index3.html)
- [FOREST TILES](https://iskillplay.web.app/forest-tiles.html)
- [DAILY CHESS PUZZLE](https://studyplayings.web.app/daily-chess-puzzle.html)
- [MINE FPS SHOOTER NOOB ARENA](https://thelearnquesters.pages.dev/mine-fps-shooter-noob-arena.html)
- [LITTLE BUGS](https://learnquester.pages.dev/little-bugs.html)
- [BIG BAD APE](https://theskillquest.pages.dev/big-bad-ape.html)
- [STICK VS MONSTER SCHOOL 2](https://themindzone.pages.dev/stick-vs-monster-school-2.html)
- [GUN CLONE](https://iskillplay.web.app/gun-clone.html)
- [JELLY RUN 2048](https://theskillquest.pages.dev/jelly-run-2048.html)
- [EXTREME MAKEOVER HARLEY EDITION](https://studyplayings.pages.dev/extreme-makeover-harley-edition.html)
- [BLOCK COMBO BLAST](https://studyplaying.github.io/block-combo-blast.html)
- [GIN RUMMY](https://thelearnquesters.pages.dev/gin-rummy.html)
- [NUTS BOLTS PUZZLE](https://themindzone.pages.dev/nuts-bolts-puzzle.html)
- [CRAZY PLANE LANDING](https://thelearnquesters.pages.dev/crazy-plane-landing.html)
- [CATEGORY AGILITY 2](https://learnquester.pages.dev/category-agility-2.html)
- [MUSCLE UP MASTER](https://iskillplay.web.app/muscle-up-master.html)
- [ANIMAL BLOCKS](https://studyplayings.web.app/animal-blocks.html)
- [CATEGORY AGILITY](https://studyplayings.pages.dev/category-agility.html)
- [SCOOTER TOUCHGRIND TRICKS 3D](https://thelearnquesters.pages.dev/scooter-touchgrind-tricks-3d.html)
- [EMERGENCY OPERATOR](https://iskillplay.web.app/emergency-operator.html)
- [THE DRAG RACING CHALLENGE](https://iskillplay.web.app/the-drag-racing-challenge.html)
- [CATEGORY PUZZLE 10](https://thequizzone.pages.dev/category-puzzle-10.html)
- [CATEGORY MAKEUP51](https://thequizzone.pages.dev/category-makeup51.html)
- [LABUBU ADVENTURE](https://iskillplay.web.app/labubu-adventure.html)
- [ENCHANTED EASTER ADVENTURE](https://learnquester.pages.dev/enchanted-easter-adventure.html)
- [WORDS OR DIE](https://studyplayings.web.app/words-or-die.html)
- [FROGGY HOP](https://thelearnquesters.pages.dev/froggy-hop.html)
- [CATEGORY AIRPLANE29](https://studyplayings.pages.dev/category-airplane29.html)
- [WITCHY SISTERS RELAX PUZZLE](https://themindzone.pages.dev/witchy-sisters-relax-puzzle.html)
- [CATEGORY MAHJONG 2](https://themindplay.github.io/category-mahjong-2.html)
- [TANGLE MASTER 3D](https://learnquesters.pages.dev/tangle-master-3d.html)
- [MY PERFECT FARM](https://thequizzone.pages.dev/my-perfect-farm.html)
- [SPRUNKI POPIT](https://iskillplay.web.app/sprunki-popit.html)
- [GOON BALL](https://thelearnquesters.pages.dev/goon-ball.html)
- [FOREST MATCH 4](https://studyplaying.github.io/forest-match-4.html)
- [ZOMBIE SPACE EPISODE II](https://thelearnquesters.pages.dev/zombie-space-episode-ii.html)
- [CRAZYSTEVEIO](https://iskillplay.web.app/crazysteveio.html)
- [NOOB VS ZOMBIE APOCALYPSE SHOOTING PRO](https://learnquester.pages.dev/noob-vs-zombie-apocalypse-shooting-pro.html)
- [PUZZLES BALLS MERGE THE NEW YEAR](https://iskillplay.web.app/puzzles-balls-merge-the-new-year.html)
- [RIDDLEMATH](https://studyplaying.github.io/riddlemath.html)
- [PIRATE PARADISE](https://studyplayings.pages.dev/pirate-paradise.html)
- [CATEGORY PUZZLE 4](https://theskillquest.pages.dev/category-puzzle-4.html)
- [BLOCK TNT BLAST](https://thelearnquesters.pages.dev/block-tnt-blast.html)
- [DOGES BATTLE ROYALE](https://theskillquest.pages.dev/doges-battle-royale.html)
- [CATEGORY MATH29](https://theskillquest.pages.dev/category-math29.html)
- [FRUIT SURVIVOR](https://iskillplay.web.app/fruit-survivor.html)
- [STEAL BRAINROT EGGS](https://theskillquest.pages.dev/steal-brainrot-eggs.html)
- [STOCKINGS DILEMMA](https://iskillplay.web.app/stockings-dilemma.html)
- [PYRAMIDZ2](https://learnquester.pages.dev/pyramidz2.html)
- [VEGAMIX2 WILD WEST](https://learnquesters.pages.dev/vegamix2-wild-west.html)
- [STICK NINJA SURVIVAL](https://thelearnquesters.pages.dev/stick-ninja-survival.html)
