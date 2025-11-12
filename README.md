# README — FitEmotional

> Diário emocional simples em Android (Jetpack Compose + Room).  
> Conteúdo e decisões arquiteturais baseados no guia do projeto.

---

## Visão geral
FitEmotional é um aplicativo Android para registro diário do estado emocional, com foco em autorreflexão e acompanhamento de humor. Permite registrar data, humor, intensidade, atividades, notas e gratidão — além de visualizar estatísticas básicas (número de entradas, intensidade média, distribuição de humores e atividades mais frequentes).

---

## Principais funcionalidades
- Registrar entradas do diário com:
  - Data (DatePicker).
  - Humor (seletor com emojis).
  - Intensidade (slider 0–10).
  - Atividades (chips selecionáveis).
  - Notas e campo de gratidão.
- Persistência local com Room (entidade `DiaryEntry`, DAO `DiaryDao`, database `AppDatabase`).
- Telas principais (Jetpack Compose):
  - Home (lista de entradas).
  - Nova Entrada (formulário).
  - Estatísticas (gráficos/medidas).
  - Perfil.
- ViewModels para lógica e exposições de dados reativas (`BNovaEntradaViewModel`, `EstatisticasViewModel`).

---

## Tecnologias
- Kotlin
- Android SDK (Compose)
- Jetpack Compose (UI)
- Room (persistência local)
- Coroutines + Flow (assincronismo / streams)
- Gradle (build)

---

## Estrutura do projeto (resumida)
```
com.example.fitemotional
├─ MainActivity.kt
├─ data
│  ├─ local
│  │  ├─ AppDatabase.kt
│  │  ├─ DiaryDao.kt
│  │  └─ Converters.kt
│  └─ model
│     └─ DiaryEntry.kt
├─ navigation
│  └─ NavGraph.kt
├─ ui
│  ├─ components/ (Cards, Selectors, Charts, etc.)
│  ├─ screens/ (AHomeDiario, BNovaEntradaScreen, CEstatisticas, CPerfil)
│  └─ theme/
└─ ui.viewmodel
   ├─ BNovaEntradaViewModel.kt
   ├─ EstatisticasViewModel.kt
   └─ EstatisticasViewModelFactory.kt
```

---

## Modelo de dados (importante)
Entidade principal: `DiaryEntry`  
Exemplo (Kotlin data class):
```kotlin
@Entity(tableName = "entries")
data class DiaryEntry(
  @PrimaryKey val date: LocalDate,
  val mood: String,
  val intensity: Float,
  val activities: List<String>,
  val notes: String,
  val gratitude: String
)
```
Conversores para `LocalDate` e `List<String>` são fornecidos em `Converters`.

---

## Principais queries / DAO
Funcionalidades expostas por `DiaryDao`:
- `insert(entry: DiaryEntry)` — insere/substitui (OnConflict REPLACE).
- `update(entry: DiaryEntry)` — atualiza.
- `delete(entry: DiaryEntry)` — deleta.
- `getAllEntries(): Flow<List<DiaryEntry>>` — todas as entradas ordenadas por data desc.
- `getEntriesByDate(date: String): Flow<List<DiaryEntry>>` — por data.
- `getAverageIntensity(): Flow<Double?>` — intensidade média.
- `getTotalEntries(): Flow<Int>` — total de entradas.
- `getMoodDistribution(): Flow<List<MoodCount>>` — distribuição de humores.

---

## Instalação / Execução (local)
1. Clone este repositório.
2. Abra no Android Studio (recomenda-se Arctic Fox ou superior; Compose está em uso).
3. Se necessário, ajuste o SDK/Gradle conforme sua máquina.
4. Build & Run (emulador ou dispositivo físico).

Comandos básicos:
```bash
# build e instalar no dispositivo/emulador conectado
./gradlew installDebug
```
> Observação: o `MainActivity` cria um `Room.databaseBuilder` com `.allowMainThreadQueries()` e `.fallbackToDestructiveMigration()` — isso facilita testes, mas **não é recomendado** para produção. Considere remover `allowMainThreadQueries()` e fornecer migrações apropriadas.

---

## Configuração do banco
`AppDatabase`:
```kotlin
@Database(entities = [DiaryEntry::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
}
```
Nome padrão do DB: `"fitemotional-db"`.

---

## Padrões arquiteturais & decisões
- UI → Compose;
- Persistência → Room + TypeConverters para `LocalDate` e `List<String>`;
- Camada de apresentação → ViewModels expondo `Flow`/`StateFlow` para telas;
- `EstatisticasViewModel` calcula métricas derivadas (média, distribuição, agrupamentos) a partir do fluxo `getAllEntries()`.

---

## Pontos de atenção / próximos passos sugeridos
- Remover `allowMainThreadQueries()` para evitar bloqueio do UI thread. Implementar DAO com `suspend` e chamadas dentro de `viewModelScope`.
- Adicionar testes unitários e instrumentados.
- Implementar migrações do Room ao evoluir o schema.
- Internacionalização (strings.xml) e temas escuros.

---

## Contribuição
1. Abra uma *issue* descrevendo a proposta.
2. Faça um fork e crie uma branch `feature/nome`.
3. Envie um pull request explicando as mudanças.

---

## Licença
Escolha uma licença (por exemplo MIT) e adicione um arquivo `LICENSE` apropriado no repositório.
