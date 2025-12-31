# 🧙‍♂️ Wizard Battle

A battle game between two wizards developed in Java, where players control characters who cast spells, collect power-ups, and compete to defeat their opponent.

## 📋 Index

- [Overview](#overview)
- [Class Diagram](#class-diagram)
- [Project Architecture](#project-architecture)
- [Class Documentation](#class-documentation)
- [How to Play](#how-to-play)
- [Build and Run](#build-and-run)
- [Controls](#controls)

---

## 🎮 Overview

Wizard Battle is a local multiplayer game where two players compete in a divided arena. Each player controls a wizard character who can:

- **Move** within their half of the arena
- **Cast spells** at the opponent
- **Collect power-ups** that appear randomly
- **Temporarily boost** their abilities through buffs

The goal is to reduce your opponent's life to zero before they do the same to you.

---

## 📊 Class Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                            CAMADA DE APLICAÇÃO                          │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
                              ┌──────────┐
                              │   App    │
                              └────┬─────┘
                                   │
                    ┌──────────────┴──────────────┐
                    │                             │
                    ▼                             ▼
        ┌─────────────────────┐       ┌──────────────────┐
        │  GameController     │       │   HomeScreen     │
        │  - startAction      │       │  - background    │
        │  + startGame()      │       │  + hide()        │
        └─────────────────────┘       └──────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                         CAMADA DE GERENCIAMENTO                         │
└─────────────────────────────────────────────────────────────────────────┘
                    │
        ┌───────────┼───────────┐
        │           │           │
        ▼           ▼           ▼
┌──────────────┐ ┌────────────────┐ ┌──────────────────┐
│GameState     │ │CollisionManager│ │PowerUpHandler    │
│Manager       │ │- characters    │ │+ handleCollection│
│+ triggerGame │ │- powerUps      │ │+ applyEffect     │
│  Over()      │ │+ checkCollision│ └──────────────────┘
└──────────────┘ └────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                         CAMADA DE PERSONAGENS                           │
└─────────────────────────────────────────────────────────────────────────┘
                    │
                    ▼
            ┌───────────────┐
            │  Character    │◄─────────────┐
            │  (abstract)   │              │
            ├───────────────┤              │
            │# position     │              │
            │# characterHead│              │
            │# healthBar    │              │
            │# playerNumber │              │
            ├───────────────┤              │
            │+ moveUp()     │              │
            │+ moveDown()   │              │
            │+ moveLeft()   │              │
            │+ moveRight()  │              │
            │+ castSpell()  │              │
            │+ takeDamage() │              │
            └───────┬───────┘              │
                    │                      │
         ┌──────────┴──────────┐          │
         │                     │          │
         ▼                     ▼          │
┌──────────────────┐  ┌──────────────────┐│
│PlayerOneCharacter│  │PlayerTwoCharacter││
│- opponent: P2    │  │- opponent: P1    ││
└──────────────────┘  └──────────────────┘│
                                          │
                    ┌─────────────────────┘
                    │
            ┌───────┴────────┐
            │  BuffManager   │
            │+ applyTempBuff │
            └────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                         CAMADA DE ELEMENTOS DE JOGO                     │
└─────────────────────────────────────────────────────────────────────────┘
         │
    ┌────┴────┐
    │         │
    ▼         ▼
┌────────┐  ┌─────────┐
│ Spell  │  │ PowerUp │◄──────┐
│        │  │         │       │
├────────┤  ├─────────┤       │
│- damage│  │- col    │       │
│- speed │  │- row    │       │
│- player│  └─────────┘       │
├────────┤       △             │
│+ move()│       │             │
└────────┘       │             │
            ┌────┴────┬────────┴─────────┐
            │         │                  │
            ▼         ▼                  ▼
    ┌──────────┐ ┌──────────┐  ┌───────────────┐
    │PowerUp   │ │PowerUp   │  │PowerUpSpell   │
    │Health    │ │Damage    │  │Speed          │
    └──────────┘ └──────────┘  └───────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                         CAMADA DE INTERFACE                             │
└─────────────────────────────────────────────────────────────────────────┘
         │
    ┌────┴────────────┐
    │                 │
    ▼                 ▼
┌────────┐      ┌──────────┐
│  Grid  │      │HealthBar │
│        │      │          │
├────────┤      ├──────────┤
│- cols  │      │- lives[] │
│- rows  │      │+ remove  │
│- canvas│      │  LifePoint│
├────────┤      └──────────┘
│+ init()│
└────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                         CAMADA DE CONTROLES                             │
└─────────────────────────────────────────────────────────────────────────┘
         │
    ┌────┴────┐
    │         │
    ▼         ▼
┌─────────────┐  ┌──────────┐
│AppKeyboard  │  │ Controls │
│- keyboard   │  │          │
│- character  │  ├──────────┤
│+ keyPressed │  │- moveUp  │
└─────────────┘  │- moveDown│
                 │- attack  │
                 └──────────┘
```

---

## 🏗️ Project Architecture

### Directory Structure

```
WizzardBattle/
├── src/
│   ├── App.java                          # Application entry point
│   ├── game/                             # Main game logic
│   │   ├── GameController.java           # Central game controller
│   │   ├── GameStateManager.java         # State management
│   │   ├── Player.java                   # Player wrapper class
│   │   ├── PlayerEnum.java               # Player enumeration
│   │   ├── characters/                   # Playable characters
│   │   │   ├── Character.java            # Abstract base class
│   │   │   ├── PlayerOneCharacter.java   # Player 1 implementation
│   │   │   ├── PlayerTwoCharacter.java   # Player 2 implementation
│   │   │   └── BuffManager.java          # Temporary buff manager
│   │   ├── spells/                       # Spell system
│   │   │   └── Spell.java                # Spell projectile
│   │   └── powerUps/                     # Power-up system
│   │       ├── PowerUp.java              # Base power-up class
│   │       ├── PowerUpHandler.java       # Centralized handler
│   │       ├── PowerUpHealth.java        # Health power-up
│   │       ├── PowerUpDamage.java        # Damage power-up
│   │       └── PowerUpSpellSpeed.java    # Spell speed power-up
│   ├── collisionManager/                 # Collision system
│   │   └── CollisionManager.java         # Collision detection
│   ├── keyboard/                         # Input system
│   │   ├── AppKeyboard.java              # Keyboard manager
│   │   └── Controls.java                 # Key mapping
│   ├── ui/                               # User interface
│   │   ├── character/                    # Character rendering
│   │   │   └── CharacterUI.java          # Character UI
│   │   ├── grid/                         # Grid system
│   │   │   ├── Grid.java                 # Main game grid
│   │   │   └── GameArea.java             # Playable area
│   │   ├── healthBar/                    # Health system
│   │   │   └── HealthBar.java            # Health bar
│   │   ├── faceCard/                     # Player avatars
│   │   │   └── PlayerFaceCard.java       # Player face card
│   │   ├── screens/                      # Game screens
│   │   │   ├── HomeScreen.java           # Home screen
│   │   │   └── GameOverScreen.java       # Game over screen
│   │   └── position/                     # Positioning system
│   │       └── Position.java             # Logical coordinates
│   └── utils/                            # Utilities
│       └── AppColor.java                 # Color palette
├── resources/                            # Graphic resources
│   ├── Characters/                       # Character sprites
│   ├── Faces/                            # Player avatars
│   ├── PowerUps/                         # Power-up sprites
│   ├── Spells/                           # Spell sprites
│   └── *.png                             # Backgrounds and UI
├── lib/                                  # External libraries
└── build.xml                             # Ant build script
```

---

## 📚 Class Documentation

### 🎯 Application Layer

#### **App.java**
**Purpose:** Main entry point of the application.

**Responsibilities:**
- Initialize the home screen (HomeScreen)
- Set up the game start callback
- Create the grid and player characters
- Initialize the collision system

**Main Methods:**
- `main(String[] args)` - Application entry method
- `startGame()` - Starts a new match, creating grid and characters

**Execution Flow:**
1. Creates and displays HomeScreen
2. Waits for SPACE key to start
3. Creates Grid (128x72 cells, 1920x1080 pixels)
4. Positions players in their halves of the arena
5. Instantiates PlayerOneCharacter and PlayerTwoCharacter

---

### 🎮 Management Layer

#### **GameController.java**
**Purpose:** Central controller that coordinates the start of the game.

**Responsibilities:**
- Store the start game action
- Provide an entry point to start matches

**Methods:**
- `setStartAction(Runnable action)` - Sets the action to execute on start
- `startGame()` - Executes the registered start action

**Usage:** Decouples screens (HomeScreen, GameOverScreen) from App initialization logic.

---

#### **GameStateManager.java**
**Purpose:** Centralizes game state transitions (mainly game-over).

**Responsibilities:**
- Manage game over
- Clear all visual and logical elements
- Display victory screen

**Methods:**
- `triggerGameOver(PlayerEnum winner)` - Triggers the game-over sequence

**Game-Over Process:**
1. Clears all health bars
2. Clears CollisionManager (characters, power-ups)
3. Clears Grid and visual elements
4. Waits 100ms for threads to finish
5. Displays GameOverScreen with the winner

---

#### **CollisionManager.java**
**Propósito:** Sistema de detecção de colisões entre todos os elementos do jogo.

**Responsabilidades:**
- Registrar personagens e power-ups ativos
- Detectar colisões entre feitiços e personagens
- Verificar coleta de power-ups
- Validar movimentos dentro da área de jogo

**Estruturas de Dados:**
- `List<Character> registeredCharacters` - Personagens ativos
- `List<PowerUp> registeredPowerUps` - Power-ups disponíveis
- `DEBUG_COLLISIONS` - Flag de debug (atualmente true)

**Métodos Principais:**

**Registro:**
- `registerCharacter(Character)` - Adiciona personagem ao sistema
- `registerPowerUp(PowerUp)` - Adiciona power-up ao sistema
- `unregisterPowerUp(PowerUp)` - Remove power-up coletado

**Detecção de Power-Ups:**
- `getPowerUpAt(int col, int row)` - Busca power-up em posição específica (raio de 1 célula)
- `getPowerUpAlongPath(fromCol, fromRow, toCol, toRow)` - Detecta power-ups ao longo de movimento
- `getPowerUpOverlappingCharacter(Character)` - Detecção por sobreposição de pixels

**Detecção de Feitiços:**
- `getCollidingCharacter(Spell)` - Verifica colisão simples feitiço-personagem
- `getCollidingCharacterAlongPath(Spell, fromCol, toCol)` - Detecção avançada considerando trajetória

**Validação de Movimento:**
- `checkGameAreaCollision(newCol, newRow)` - Verifica se movimento é válido
  - Considera limites lógicos (colunas/linhas)
  - Considera limites físicos (pixels)
  - Aplica padding extra para hitbox dos personagens
  - Respeita divisão de arena por jogador

**Limpeza:**
- `clearAll()` - Remove todos os elementos registrados
- `dumpState()` - Método de debug (vazio após remoção de logs)

**Algoritmos de Colisão:**

1. **Colisão Feitiço-Personagem:**
   - Calcula área varrida pelo feitiço (swept rectangle)
   - Usa posição anterior e atual do feitiço
   - Adiciona padding de hitbox (8 pixels para feitiço, 12 para personagem)
   - Adiciona padding vertical de meia célula

2. **Colisão Power-Up:**
   - Busca em raio de células configurável (POWER_UP_PICKUP_RADIUS_CELLS)
   - Fallback para detecção por sobreposição de pixels
   - Suporta movimento multi-célula (quando velocidade > 1)

---

#### **PowerUpHandler.java**
**Propósito:** Centraliza toda a lógica de coleta e aplicação de power-ups.

**Responsabilidades:**
- Verificar coleta de power-ups durante movimento
- Aplicar efeitos apropriados ao personagem
- Remover power-up do jogo após coleta

**Métodos:**
- `handlePowerUpCollection(Character, fromCol, fromRow, toCol, toRow)` - Verifica e processa coleta
- `applyPowerUpEffect(Character, PowerUp)` - Aplica efeito do power-up

**Efeitos por Tipo:**
- **PowerUpHealth:** `character.addLifePoints()`
- **PowerUpDamage:** `character.applyDamageBuff(1, 10s)`
- **PowerUpSpellSpeed:** `character.applySpeedBuff(1, 10s)` + `applyMovementBuff(1, 10s)`

**Benefício:** Elimina duplicação de código (antes: 8 lugares, agora: 1 lugar).

---

### 👤 Character Layer

#### **Character.java (Classe Abstrata)**
**Propósito:** Classe base que contém toda a lógica comum aos personagens.

**Atributos Protegidos:**
```java
protected CharacterUI characterHead;      // Sprite visual
protected Position position;              // Posição lógica (col, row)
protected PlayerEnum playerNumber;        // Identificador do jogador
protected AppKeyboard appKeyboard;        // Sistema de input
protected CollisionManager collisionManager; // Gerenciador de colisões
protected HealthBar healthBar;            // Barra de vida
```

**Atributos de Buffs:**
```java
private int spellDamageModifier = 0;      // Buff de dano (+1 = 2 de dano total)
private int spellSpeedModifier = 0;       // Buff de velocidade de feitiço
private int movementSpeedModifier = 0;    // Buff de velocidade de movimento
```

**Métodos Abstratos** (devem ser implementados pelas subclasses):
- `Position getPosition()` - Retorna posição atual
- `int getPixelX/Y/Width/Height()` - Retorna bounds em pixels
- `PlayerEnum getOpponentPlayer()` - Retorna enum do oponente
- `void takeDamage(int damage)` - Processa dano recebido
- `void addLifePoints()` - Adiciona vida ao personagem

**Métodos Concretos de Movimento:**

Todos os métodos de movimento seguem o mesmo padrão:

```java
public void moveUp/Down/Left/Right() {
    // 1. Calcula células a mover (1 + buff de velocidade)
    int moveCells = 1 + Math.max(0, getMovementSpeedModifier());
    
    // 2. Calcula nova posição
    int newRow/Col = position + (±moveCells);
    
    // 3. Valida movimento
    if (collisionManager.checkGameAreaCollision(newCol, newRow)) {
        // 4. Move sprite visual
        characterHead.move(deltaX, deltaY);
        
        // 5. Verifica coleta de power-ups
        PowerUpHandler.handlePowerUpCollection(...);
        
        // 6. Atualiza posição lógica
        position.setRow/Col(newValue);
    }
}
```

**Método de Feitiço:**
```java
public void castSpell() {
    Spell s = new Spell(position.getRow(), position.getCol(), playerNumber);
    s.setDamage(s.getDamage() + getSpellDamageModifier());
    s.setSpeed(s.getSpeed() + getSpellSpeedModifier());
}
```

**Sistema de Buffs:**
- `applyDamageBuff(extraDamage, durationSeconds)` - Aumenta dano temporariamente
- `applySpeedBuff(extraSpeed, durationSeconds)` - Aumenta velocidade de feitiço
- `applyMovementBuff(extraMovement, durationSeconds)` - Aumenta velocidade de movimento

Todos os buffs usam `BuffManager.applyTemporaryBuff()` internamente.

**Outros Métodos:**
- `cleanupOnGameOver()` - Hook para limpeza no fim do jogo
- `hideCharacter()` - Esconde sprite do personagem

---

#### **PlayerOneCharacter.java / PlayerTwoCharacter.java**
**Propósito:** Implementações concretas para cada jogador.

**Diferenças:**
- **Sprite:** `character.png` vs `character2.png`
- **Controles:** WASD+T vs IJKL+P
- **Oponente:** Player_2 vs Player_1

**Construtor:**
```java
public PlayerOneCharacter(Grid grid, int column, int row) {
    playerNumber = PlayerEnum.Player_1;
    position = new Position(column, row);
    characterHead = new CharacterUI(column, row, "resources/Characters/character.png");
    appKeyboard = new AppKeyboard(PlayerEnum.Player_1, this);
    collisionManager = new CollisionManager(this, grid);
    healthBar = new HealthBar(PlayerEnum.Player_1);
}
```

**Implementação de takeDamage:**
```java
public void takeDamage(int damage) {
    healthBar.removeLifePoints(damage);
    if (!healthBar.isAlive()) {
        hideCharacter();
        GameStateManager.triggerGameOver(getOpponentPlayer());
    }
}
```

**Métricas:** Cada classe tem apenas ~65-70 linhas (redução de 74% após refatoração).

---

#### **BuffManager.java**
**Propósito:** Gerenciador genérico de buffs temporários.

**Interface ModifiableValue:**
```java
public interface ModifiableValue {
    void modify(int delta); // delta pode ser positivo (aplicar) ou negativo (remover)
}
```

**Método Principal:**
```java
public static void applyTemporaryBuff(
    ModifiableValue target,   // O que modificar
    int extraValue,           // Quanto adicionar
    int durationSeconds       // Por quanto tempo
)
```

**Funcionamento:**
1. Aplica buff imediatamente: `target.modify(+extraValue)`
2. Cria thread que aguarda duração
3. Remove buff: `target.modify(-extraValue)`

**Uso Típico:**
```java
applyDamageBuff(1, 10) {
    BuffManager.applyTemporaryBuff(delta -> {
        synchronized (this) {
            spellDamageModifier = Math.max(0, spellDamageModifier + delta);
        }
    }, 1, 10);
}
```

**Benefício:** Elimina duplicação de 3 métodos similares em Character.

---

### 🔮 Game Elements Layer

#### **Spell.java**
**Propósito:** Projétil de feitiço que se move horizontalmente e causa dano.

**Atributos:**
```java
private Picture spell;           // Sprite visual
private Position position;       // Posição lógica
private int prevX;              // Posição X anterior (para swept collision)
private int speed;              // Células por tick (padrão: 2)
private int damage;             // Dano causado (padrão: 1)
private PlayerEnum playerEnum;  // Dono do feitiço
```

**Gerenciamento Global:**
```java
private static final List<Spell> ACTIVE; // Todos os feitiços ativos
public static void cleanupAll();         // Remove todos ao fim do jogo
```

**Construtor:**
```java
public Spell(int row, int col, PlayerEnum playerEnum)
```

**Cálculo de Posição Inicial:**
1. Calcula posição base da célula
2. Adiciona offset vertical (SPELL_VERTICAL_OFFSET_BASE ou _P2)
3. Ajusta horizontalmente para simular disparo da mão:
   - Player 1: dispara da mão direita (+handOffset)
   - Player 2: dispara da mão esquerda (-handOffset)

**Thread de Movimento:**

O feitiço cria uma thread que:

1. **Loop Principal:**
   ```java
   while (true) {
       // Calcula próxima posição
       int desiredNext = currentCol + (dir * speed);
       
       // Verifica limites da arena
       if (fora_dos_limites) {
           // Move até a borda
           // Aguarda 40ms
           // Remove feitiço
           break;
       }
       
       // Verifica colisão com personagem
       Character hit = CollisionManager.getCollidingCharacterAlongPath(...);
       if (hit != null) {
           // Move até o alvo
           hit.takeDamage(damage);
           // Remove feitiço
           break;
       }
       
       // Move normalmente
       position.setCol(desiredNext);
       translate(dir * CELL_SIZE * speed, 0);
       
       // Aguarda 60ms antes do próximo tick
       Thread.sleep(60);
   }
   ```

2. **Direção:**
   - Player 1: `dir = 1` (direita →)
   - Player 2: `dir = -1` (esquerda ←)

3. **Velocidade:** 2 células a cada 60ms = ~33 células/segundo

**Métodos Auxiliares:**
- `translate(col, row)` - Move sprite e atualiza prevX
- `safeDelete()` - Remove sprite com tratamento de erro
- `getX/Y/Width/Height()` - Getters para detecção de colisão
- `getPrevX()` - Posição anterior para swept collision

---

#### **PowerUp.java (Classe Base)**
**Propósito:** Classe base para todos os power-ups coletáveis.

**Atributos:**
```java
private final int col;              // Coluna lógica
private final int row;              // Linha lógica
private Picture powerUpSquare;      // Sprite visual
private static final List<PowerUp> ACTIVE; // Todos power-ups ativos
```

**Construtor:**
```java
public PowerUp(int col, int row, String imagePath) {
    this.row = row;
    this.col = col;
    
    // Calcula posição centralizada na célula
    int x = Grid.PADDING + col * Grid.CELL_SIZE + (Grid.CELL_SIZE - size) / 2;
    int y = Grid.PADDING + row * Grid.CELL_SIZE + (Grid.CELL_SIZE - size) / 2;
    
    powerUpSquare = new Picture(x, y, imagePath);
    powerUpSquare.draw();
    
    CollisionManager.registerPowerUp(this);
    ACTIVE.add(this);
}
```

**Métodos:**
- `removeFromGame()` - Remove power-up após coleta
  1. Deleta sprite
  2. Remove do CollisionManager
  3. Notifica Grid
- `getPixelX/Y/Width/Height()` - Bounds para detecção de colisão
- `cleanupAll()` - Remove todos power-ups (fim de jogo)

**Subclasses:**

1. **PowerUpHealth** (`resources/PowerUps/health.png`)
   - Efeito: +1 ponto de vida
   
2. **PowerUpDamage** (`resources/PowerUps/damage.png`)
   - Efeito: +1 dano por 10 segundos
   
3. **PowerUpSpellSpeed** (`resources/PowerUps/spellSpeed.png`)
   - Efeito: +1 velocidade de feitiço e movimento por 10 segundos

**Sistema de Spawn:**
- Power-ups são criados pelo Grid
- Delay entre spawns: `POWER_UP_SPAWN_DELAY_SECONDS = 8`
- Duração de buffs: `POWER_UP_BUFF_DURATION_SECONDS = 10`
- Raio de coleta: `POWER_UP_PICKUP_RADIUS_CELLS = 1`

---

### 🖼️ Interface Layer

#### **Grid.java**
**Propósito:** Sistema central de renderização e coordenação da arena.

**Constantes Importantes:**
```java
public static final int PADDING = 10;
public static final int DEFAULT_CELL_SIZE = 5;
public static int CELL_SIZE;                              // Definido em runtime

// Power-Ups
public static final int POWER_UP_SPAWN_DELAY_SECONDS = 8;
public static final int POWER_UP_BUFF_DURATION_SECONDS = 10;
public static final int POWER_UP_PICKUP_RADIUS_CELLS = 1;

// Hitboxes
public static final int EXTRA_HIT_BOX_PADDING_CHAR_PIXELS = 12;
public static final int EXTRA_HIT_BOX_PADDING_SPELL_PIXELS = 8;

// Alinhamento de Feitiços
public static final int SPELL_VERTICAL_OFFSET_BASE = 35;
public static final int SPELL_VERTICAL_OFFSET_P2 = 40;
public static final int SPELL_HAND_TUNING = 6;
```

**Atributos:**
```java
private static int cols;              // Colunas totais
private static int rows;              // Linhas totais
private int cellSize;                 // Tamanho de cada célula
private static Picture canvas;        // Background
private GameArea gameArea;            // Área jogável central
private static PowerUp leftPowerUp;   // Power-up esquerdo ativo
private static PowerUp rightPowerUp;  // Power-up direito ativo
private PlayerFaceCard card1, card2;  // Avatares dos jogadores
```

**Construtor e Inicialização:**

```java
// Construtor calcula tamanho de célula baseado na resolução alvo
public Grid(int cols, int rows, int targetWidth, int targetHeight) {
    Grid.cols = cols;
    Grid.rows = rows;
    this.targetWidth = targetWidth - 2 * PADDING;
    this.targetHeight = targetHeight - 2 * PADDING;
    
    // Calcula maior célula que cabe
    int sizeByWidth = this.targetWidth / cols;
    int sizeByHeight = this.targetHeight / rows;
    this.cellSize = Math.min(sizeByWidth, sizeByHeight);
}

// Inicialização cria todos os elementos visuais
public void init() {
    CELL_SIZE = cellSize;
    
    // 1. Canvas de fundo
    canvas = new Picture(PADDING, PADDING, "resources/backgroun2.png");
    canvas.draw();
    
    // 2. GameArea (área jogável)
    gameArea = new GameArea("resources/GameArea.png", ...);
    
    // 3. Avatares dos jogadores
    card1 = new PlayerFaceCard(...);
    card2 = new PlayerFaceCard(...);
    
    // 4. Inicia spawn de power-ups
    startPowerUpSpawning();
}
```

**Sistema de GameArea:**

A GameArea é a região central onde os personagens podem se mover.

```java
// Cálculo da área de jogo
public int getGameAreaRows() {
    int areaHeight = gameArea.getAreaHeight();
    return Math.max(0, areaHeight / cellSize);
}

public int getGameAreaTopRow() {
    int totalRows = rows;
    int gameRows = getGameAreaRows();
    return (totalRows - gameRows) / 2; // Centralizado verticalmente
}

public int getGameAreaBottomRow() {
    return getGameAreaTopRow() + getMaxRowsPerPlayer() - 1;
}

// Cada jogador tem metade das colunas
public int getMaxColsPerPlayer() {
    return Math.max(1, cols / 2);
}

// Cada jogador tem todas as linhas da game area
public int getMaxRowsPerPlayer() {
    return getGameAreaRows();
}
```

**Sistema de Power-Ups:**

```java
private void startPowerUpSpawning() {
    powerUpSpawningEnabled = true;
    
    new Thread(() -> {
        while (powerUpSpawningEnabled) {
            try {
                Thread.sleep(POWER_UP_SPAWN_DELAY_SECONDS * 1000);
                if (powerUpSpawningEnabled) {
                    spawnRandomPowerUps();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }).start();
}

private void spawnRandomPowerUps() {
    // Escolhe tipo aleatório
    PowerUp[] types = {
        new PowerUpHealth(...),
        new PowerUpDamage(...),
        new PowerUpSpellSpeed(...)
    };
    
    // Spawn em posições aleatórias
    // - leftPowerUp: lado esquerdo da arena
    // - rightPowerUp: lado direito da arena
}
```

**Métodos Úteis:**
- `getCols/Rows()` - Dimensões da grid
- `getWidth/Height()` - Tamanho em pixels do canvas
- `getActiveGrid()` - Retorna instância ativa da grid
- `clearAll()` - Remove todos elementos visuais
- `onPowerUpCollected(PowerUp)` - Callback quando power-up é coletado

---

#### **HealthBar.java**
**Propósito:** Sistema de visualização de pontos de vida.

**Estrutura:**
```java
public class HealthBar {
    private Life[] lifeCounter;           // Array de ícones de vida
    private PlayerEnum playerNumber;      // Dono da barra
    private int numberOfLives = 10;       // Vidas totais (padrão)
    
    // Lista global para limpeza
    private static List<HealthBar> INSTANCES;
    
    // Classe interna para cada ícone de vida
    class Life {
        private Picture life;
        public Life(int index, boolean playerOne) { ... }
        public void remove() { ... }
    }
}
```

**Layout:**

```
Player 1 (esquerda):          Player 2 (direita):
❤❤❤❤❤❤❤❤❤❤                 ❤❤❤❤❤❤❤❤❤❤
```

- Player 1: Vidas alinhadas da esquerda para direita
- Player 2: Vidas alinhadas da direita para esquerda

**Construtor:**
```java
public HealthBar(PlayerEnum playerNumber) {
    this.playerNumber = playerNumber;
    lifeCounter = new Life[numberOfLives];
    
    // Cria todos os ícones de vida
    for (int i = 0; i < numberOfLives; i++) {
        lifeCounter[i] = new Life(i, playerNumber == PlayerEnum.Player_1);
    }
    
    INSTANCES.add(this);
}
```

**Métodos Principais:**

1. **removeLifePoints(int pointsToRemove)**
   - Player 1: Remove do fim para o início (direita → esquerda)
   - Player 2: Remove do início para o fim (esquerda → direita)
   
2. **isAlive()**
   - Retorna true se ainda há algum ícone de vida

3. **addLifePoints()**
   - Algoritmo inteligente de preenchimento:
     1. Se vazio, adiciona na primeira/última posição
     2. Player 1: Tenta adicionar após o último preenchido
     3. Player 2: Tenta adicionar antes do primeiro preenchido
     4. Fallback: Preenche primeiro slot vazio encontrado

**Limpeza:**
- `cleanup()` - Remove todos os ícones de uma barra
- `cleanupAll()` - Remove todas as barras (chamado em game-over)

---

#### **Screens (HomeScreen e GameOverScreen)**

**HomeScreen.java:**
```java
public class HomeScreen {
    private Picture background;
    private Picture title;
    private Text startHint;
    private boolean visible = true;
    
    public HomeScreen() {
        // Carrega background
        background = new Picture(0, 0, "resources/backgroun2.png");
        background.draw();
        
        // Carrega título
        title = new Picture(x, y, "resources/WizardBattle.png");
        title.draw();
        
        // Texto de instrução
        startHint = new Text(x, y, "Press SPACE to start");
        startHint.setColor(Color.WHITE);
        startHint.draw();
    }
    
    public void hide() {
        if (visible) {
            background.delete();
            title.delete();
            startHint.delete();
            visible = false;
        }
    }
}
```

**GameOverScreen.java:**
```java
public class GameOverScreen implements KeyboardHandler {
    private Picture background;
    private Picture winnerFace;
    private Text winnerText;
    private Text hintText;
    private boolean triggered = false;
    
    public GameOverScreen(PlayerEnum winner) {
        // 1. Background
        background = new Picture(0, 0, "resources/backgroun2.png");
        background.draw();
        
        // 2. Face do vencedor (ampliada)
        String facePath = Grid.getPlayerFacePath(winner);
        winnerFace = new Picture(centerX, centerY, facePath);
        winnerFace.draw();
        winnerFace.grow(120, 120);
        
        // 3. Texto de vitória
        winnerText = new Text(x, y, playerName + " WINS!");
        winnerText.setColor(Color.YELLOW);
        winnerText.draw();
        
        // 4. Hint de restart
        hintText = new Text(x, y, "Press SPACE to restart");
        hintText.draw();
        
        // 5. Escuta SPACE para reiniciar
        setupKeyboardListener();
    }
    
    public void keyPressed(KeyboardEvent event) {
        if (!triggered && event.getKey() == KeyboardEvent.KEY_SPACE) {
            triggered = true;
            cleanup();
            GameController.startGame();
        }
    }
}
```

---

### ⌨️ Controls Layer

#### **AppKeyboard.java**
**Propósito:** Gerenciador de entrada de teclado por personagem.

**Atributos:**
```java
private Keyboard keyboard;              // Listener do SimpleGraphics
private Controls playerControls;        // Mapeamento de teclas
private Character controlledCharacter;  // Personagem controlado
```

**Sistema Global de SPACE:**
```java
private static Keyboard globalKeyboard;
private static boolean armedForStart = false;
private static HomeScreen armedHomeScreen = null;
private static Runnable armedAction = null;
```

**Construtor:**
```java
public AppKeyboard(PlayerEnum playerNumber, Character character) {
    keyboard = new Keyboard(this);
    playerControls = new Controls(playerNumber);
    this.controlledCharacter = character;
    
    // Registra eventos
    keyboard.addEventListener(playerControls.getMoveUpEvent());
    keyboard.addEventListener(playerControls.getMoveDownEvent());
    keyboard.addEventListener(playerControls.getMoveLeftEvent());
    keyboard.addEventListener(playerControls.getMoveRightEvent());
    keyboard.addEventListener(playerControls.getAttackEvent());
}
```

**Handler de Eventos:**
```java
public void keyPressed(KeyboardEvent event) {
    int key = event.getKey();
    
    if (key == playerControls.getMoveRightEvent().getKey()) {
        controlledCharacter.moveRight();
    } else if (key == playerControls.getMoveLeftEvent().getKey()) {
        controlledCharacter.moveLeft();
    } else if (key == playerControls.getMoveUpEvent().getKey()) {
        controlledCharacter.moveUp();
    } else if (key == playerControls.getMoveDownEvent().getKey()) {
        controlledCharacter.moveDown();
    } else if (key == playerControls.getAttackEvent().getKey()) {
        controlledCharacter.castSpell();
    }
}
```

**Sistema de Start:**
```java
public static void addStartListener(HomeScreen screen, Runnable action) {
    // Inicializa listener global de SPACE uma única vez
    // Permite "armar" o listener para start/restart
    armedHomeScreen = screen;
    armedAction = action;
    armedForStart = true;
}
```

---

#### **Controls.java**
**Propósito:** Configuração de mapeamento de teclas por jogador.

**Estrutura de Configuração:**
```java
private static class ControlConfig {
    int up, down, left, right, attack;
    
    ControlConfig(int up, int down, int left, int right, int attack) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.attack = attack;
    }
}
```

**Configurações Pré-Definidas:**
```java
private static final ControlConfig PLAYER1_CONFIG = new ControlConfig(
    KeyboardEvent.KEY_W,  // Cima
    KeyboardEvent.KEY_S,  // Baixo
    KeyboardEvent.KEY_A,  // Esquerda
    KeyboardEvent.KEY_D,  // Direita
    KeyboardEvent.KEY_T   // Ataque
);

private static final ControlConfig PLAYER2_CONFIG = new ControlConfig(
    KeyboardEvent.KEY_I,  // Cima
    KeyboardEvent.KEY_K,  // Baixo
    KeyboardEvent.KEY_J,  // Esquerda
    KeyboardEvent.KEY_L,  // Direita
    KeyboardEvent.KEY_P   // Ataque
);
```

**Construtor:**
```java
public Controls(PlayerEnum playerNumber) {
    // Seleciona configuração apropriada
    ControlConfig config = playerNumber.equals(PlayerEnum.Player_1) 
        ? PLAYER1_CONFIG 
        : PLAYER2_CONFIG;
    
    // Configura todos os eventos
    setupKeyEvent(moveUp, config.up);
    setupKeyEvent(moveDown, config.down);
    setupKeyEvent(moveLeft, config.left);
    setupKeyEvent(moveRight, config.right);
    setupKeyEvent(attack, config.attack);
}

private void setupKeyEvent(KeyboardEvent event, int key) {
    event.setKey(key);
    event.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
}
```

**Vantagens da Abordagem:**
- Fácil adicionar novos jogadores
- Configuração centralizada
- Sem duplicação de código
- Fácil modificar esquema de controles

---

## 🎮 How to Play

### Objective
Reduce your opponent's life to zero before they do the same to you!

### Controls

#### Player 1 (Left)
- **W** - Move up
- **S** - Move down
- **A** - Move left
- **D** - Move right
- **T** - Cast spell

#### Player 2 (Right)
- **I** - Move up
- **K** - Move down
- **J** - Move left
- **L** - Move right
- **P** - Cast spell

#### Global
- **SPACE** - Start game / Restart after game-over

### Power-Ups

Power-ups appear randomly in the arena every 8 seconds:

- **❤️ Health (Green):** Adds +1 life point
- **⚔️ Damage (Red):** Increases spell damage for 10 seconds
- **⚡ Speed (Blue):** Increases movement and spell speed for 10 seconds

### Strategy Tips

1. **Movement:** Characters can only move within their half of the arena
2. **Spells:** Travel in a straight line and deal 1 damage (2 with buff)
3. **Power-Ups:** Appear on both sides - be quick to collect them
4. **Buffs:** Stack! Collecting multiple increases effects
5. **Positioning:** Keep your distance when casting spells

---

## 🛠️ Build and Run

### Prerequisites
- **Java JDK 8+**
- **Apache Ant** (for build)
- **SimpleGraphics** library (included in `lib/`)

### Compile the Project

```bash
# Compile only
ant compile

# Compile and create JAR
ant jarfile

# Clean build
ant clean
```

### Run

```bash
# Run from compiled classes
java -cp "build/classes;lib/*" App

# Or run the JAR (after ant jarfile)
java -jar "build/Wizard Battle.jar"
```

### Build Structure

The `build.xml` file defines:
- **init:** Creates build directories
- **prepare:** Prepares structure
- **copy-resources:** Copies images and resources to build/classes
- **compile:** Compiles Java code
- **jarfile:** Creates executable JAR

---


## 📝 Refactoring History

This project has undergone significant refactoring to improve code quality:

### Improvements Implemented

1. **Elimination of Duplication**
    - Reduced 350+ lines of duplicated code
    - PlayerOneCharacter: 254 → 68 lines (-73%)
    - PlayerTwoCharacter: 248 → 65 lines (-74%)

2. **New Utility Classes**
    - `PowerUpHandler` - Centralizes power-up logic
    - `BuffManager` - Generic temporary buff system
    - `GameStateManager` - Game state management

3. **Logic Consolidation**
    - Common movement in the `Character` class
    - Power-ups processed in a single place
    - Centralized game-over logic

4. **Data-Driven Configuration**
    - Controls configured by data objects
    - Easy to add new players or control schemes


---

## 🤝 Contribuindo

Para contribuir com o projeto:

1. Faça fork do repositório
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona NovaFeature'`)
4. Push para a branch (`git push origin feature/NovaFeature`)
5. Abra um Pull Request

### Diretrizes

- Mantenha o código limpo e bem documentado
- Siga os padrões de código existentes
- Teste suas mudanças antes de commitar
- Atualize a documentação conforme necessário

---

## 📄 Licença

Este projeto é livre para uso educacional e pessoal.

---

## 👨‍💻 Autores

Desenvolvido como projeto acadêmico para aprendizado de:
- Programação Orientada a Objetos
- Padrões de Design
- Desenvolvimento de Jogos 2D
---



**Divirte-te🧙‍♂️⚡🧙‍♀️**
