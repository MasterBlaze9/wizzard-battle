# 🧙‍♂️ Wizard Battle

Um jogo de batalha entre dois magos desenvolvido em Java, onde os jogadores controlam personagens que lançam feitiços, coletam power-ups e competem para vencer o oponente.

## 📋 Índice

- [Visão Geral](#visão-geral)
- [Diagrama de Classes](#diagrama-de-classes)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Documentação das Classes](#documentação-das-classes)
- [Como Jogar](#como-jogar)
- [Compilação e Execução](#compilação-e-execução)
- [Controles](#controles)

---

## 🎮 Visão Geral

Wizard Battle é um jogo multiplayer local onde dois jogadores competem em uma arena dividida. Cada jogador controla um personagem mago que pode:

- **Movimentar-se** pela sua metade da arena
- **Lançar feitiços** contra o oponente
- **Coletar power-ups** que aparecem aleatoriamente
- **Aumentar temporariamente** suas habilidades através de buffs

O objetivo é reduzir a vida do oponente a zero antes que ele faça o mesmo com você.

---

## 📊 Diagrama de Classes

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

## 🏗️ Arquitetura do Projeto

### Estrutura de Diretórios

```
WizzardBattle/
├── src/
│   ├── App.java                          # Ponto de entrada da aplicação
│   ├── game/                             # Lógica principal do jogo
│   │   ├── GameController.java           # Controlador central do jogo
│   │   ├── GameStateManager.java         # Gerenciamento de estados
│   │   ├── Player.java                   # Classe wrapper de jogador
│   │   ├── PlayerEnum.java               # Enumeração de jogadores
│   │   ├── characters/                   # Personagens jogáveis
│   │   │   ├── Character.java            # Classe abstrata base
│   │   │   ├── PlayerOneCharacter.java   # Implementação Jogador 1
│   │   │   ├── PlayerTwoCharacter.java   # Implementação Jogador 2
│   │   │   └── BuffManager.java          # Gerenciador de buffs temporários
│   │   ├── spells/                       # Sistema de feitiços
│   │   │   └── Spell.java                # Projétil de feitiço
│   │   └── powerUps/                     # Sistema de power-ups
│   │       ├── PowerUp.java              # Classe base de power-up
│   │       ├── PowerUpHandler.java       # Gerenciador centralizado
│   │       ├── PowerUpHealth.java        # Power-up de vida
│   │       ├── PowerUpDamage.java        # Power-up de dano
│   │       └── PowerUpSpellSpeed.java    # Power-up de velocidade
│   ├── collisionManager/                 # Sistema de colisões
│   │   └── CollisionManager.java         # Detecção de colisões
│   ├── keyboard/                         # Sistema de entrada
│   │   ├── AppKeyboard.java              # Gerenciador de teclado
│   │   └── Controls.java                 # Mapeamento de teclas
│   ├── ui/                               # Interface do usuário
│   │   ├── character/                    # Renderização de personagens
│   │   │   └── CharacterUI.java          # UI do personagem
│   │   ├── grid/                         # Sistema de grid
│   │   │   ├── Grid.java                 # Grade principal do jogo
│   │   │   └── GameArea.java             # Área jogável
│   │   ├── healthBar/                    # Sistema de vida
│   │   │   └── HealthBar.java            # Barra de vida
│   │   ├── faceCard/                     # Avatares dos jogadores
│   │   │   └── PlayerFaceCard.java       # Card com face do jogador
│   │   ├── screens/                      # Telas do jogo
│   │   │   ├── HomeScreen.java           # Tela inicial
│   │   │   └── GameOverScreen.java       # Tela de fim de jogo
│   │   └── position/                     # Sistema de posicionamento
│   │       └── Position.java             # Coordenadas lógicas
│   └── utils/                            # Utilitários
│       └── AppColor.java                 # Paleta de cores
├── resources/                            # Recursos gráficos
│   ├── Characters/                       # Sprites de personagens
│   ├── Faces/                           # Avatares dos jogadores
│   ├── PowerUps/                        # Sprites de power-ups
│   ├── Spells/                          # Sprites de feitiços
│   └── *.png                            # Backgrounds e UI
├── lib/                                 # Bibliotecas externas
└── build.xml                            # Script de build Ant
```

---

## 📚 Documentação das Classes

### 🎯 Camada de Aplicação

#### **App.java**
**Propósito:** Ponto de entrada principal da aplicação.

**Responsabilidades:**
- Inicializar a tela inicial (HomeScreen)
- Configurar o callback de início do jogo
- Criar a grid e os personagens dos jogadores
- Inicializar o sistema de colisões

**Métodos Principais:**
- `main(String[] args)` - Método de entrada da aplicação
- `startGame()` - Inicia uma nova partida, criando grid e personagens

**Fluxo de Execução:**
1. Cria e exibe HomeScreen
2. Aguarda tecla SPACE para iniciar
3. Cria Grid (128x72 células, 1920x1080 pixels)
4. Posiciona jogadores em suas metades da arena
5. Instancia PlayerOneCharacter e PlayerTwoCharacter

---

### 🎮 Camada de Gerenciamento

#### **GameController.java**
**Propósito:** Controlador central que coordena o início do jogo.

**Responsabilidades:**
- Armazenar a ação de início de jogo
- Fornecer ponto de entrada para iniciar partidas

**Métodos:**
- `setStartAction(Runnable action)` - Define a ação a ser executada ao iniciar
- `startGame()` - Executa a ação de início registrada

**Uso:** Desacopla as telas (HomeScreen, GameOverScreen) da lógica de inicialização do App.

---

#### **GameStateManager.java**
**Propósito:** Centraliza transições de estado do jogo (principalmente game-over).

**Responsabilidades:**
- Gerenciar fim de jogo
- Limpar todos os elementos visuais e lógicos
- Exibir tela de vitória

**Métodos:**
- `triggerGameOver(PlayerEnum winner)` - Aciona sequência de game-over

**Processo de Game-Over:**
1. Limpa todas as barras de vida
2. Limpa CollisionManager (personagens, power-ups)
3. Limpa Grid e elementos visuais
4. Aguarda 100ms para threads finalizarem
5. Exibe GameOverScreen com o vencedor

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

### 👤 Camada de Personagens

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

### 🔮 Camada de Elementos de Jogo

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

### 🖼️ Camada de Interface

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

### ⌨️ Camada de Controles

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

## 🎮 Como Jogar

### Objetivo
Reduza a vida do oponente a zero antes que ele faça o mesmo com você!

### Controles

#### Jogador 1 (Esquerda)
- **W** - Mover para cima
- **S** - Mover para baixo
- **A** - Mover para esquerda
- **D** - Mover para direita
- **T** - Lançar feitiço

#### Jogador 2 (Direita)
- **I** - Mover para cima
- **K** - Mover para baixo
- **J** - Mover para esquerda
- **L** - Mover para direita
- **P** - Lançar feitiço

#### Global
- **SPACE** - Iniciar jogo / Reiniciar após game-over

### Power-Ups

Power-ups aparecem aleatoriamente na arena a cada 8 segundos:

- **❤️ Vida (Verde):** Adiciona +1 ponto de vida
- **⚔️ Dano (Vermelho):** Aumenta dano dos feitiços por 10 segundos
- **⚡ Velocidade (Azul):** Aumenta velocidade de movimento e feitiços por 10 segundos

### Dicas Estratégicas

1. **Movimento:** Personagens podem se mover apenas em sua metade da arena
2. **Feitiços:** Viajam em linha reta e causam 1 de dano (2 com buff)
3. **Power-Ups:** Aparecem dos dois lados - seja rápido para coletar
4. **Buffs:** Acumulam! Coletar múltiplos aumenta os efeitos
5. **Posicionamento:** Mantenha distância ao lançar feitiços

---

## 🛠️ Compilação e Execução

### Pré-requisitos
- **Java JDK 8+**
- **Apache Ant** (para build)
- Biblioteca **SimpleGraphics** (incluída em `lib/`)

### Compilar o Projeto

```bash
# Compilar apenas
ant compile

# Compilar e criar JAR
ant jarfile

# Limpar build
ant clean
```

### Executar

```bash
# Executar a partir das classes compiladas
java -cp "build/classes;lib/*" App

# Ou executar o JAR (após ant jarfile)
java -jar "build/Wizard Battle.jar"
```

### Estrutura do Build

O arquivo `build.xml` define:
- **init:** Cria diretórios de build
- **prepare:** Prepara estrutura
- **copy-resources:** Copia imagens e recursos para build/classes
- **compile:** Compila código Java
- **jarfile:** Cria JAR executável

---

## 📝 Histórico de Refatoração

Este projeto passou por uma refatoração significativa para melhorar a qualidade do código:

### Melhorias Implementadas

1. **Eliminação de Duplicação**
   - Redução de 350+ linhas de código duplicado
   - PlayerOneCharacter: 254 → 68 linhas (-73%)
   - PlayerTwoCharacter: 248 → 65 linhas (-74%)

2. **Novas Classes Utilitárias**
   - `PowerUpHandler` - Centraliza lógica de power-ups
   - `BuffManager` - Sistema genérico de buffs temporários
   - `GameStateManager` - Gerenciamento de estados do jogo

3. **Consolidação de Lógica**
   - Movimentação comum na classe `Character`
   - Power-ups processados em um único lugar
   - Game-over centralizado

4. **Configuração Data-Driven**
   - Controles configurados por objetos de dados
   - Fácil adicionar novos jogadores ou esquemas de controle


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
