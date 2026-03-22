package com.typersimulator.data;

import java.util.*;

public class WordBank {

    private static final String[] EASY = {
        "the", "and", "for", "are", "but", "not", "you", "all", "can", "had",
        "her", "was", "one", "our", "out", "day", "get", "has", "him", "his",
        "how", "its", "may", "new", "now", "old", "see", "two", "way", "who",
        "boy", "did", "own", "say", "she", "too", "use", "man", "run", "let",
        "put", "set", "end", "why", "ask", "big", "low", "off", "add", "age",
        "air", "bad", "bed", "buy", "car", "cut", "dog", "due", "eat", "eye",
        "far", "few", "fit", "fly", "fun", "got", "guy", "hit", "hot", "job",
        "key", "kid", "law", "lay", "leg", "lot", "map", "met", "mom", "net",
        "nor", "not", "oil", "pay", "per", "pot", "red", "rid", "row", "sad",
        "sea", "set", "sex", "son", "sun", "tax", "ten", "tie", "top", "try",
        "war", "win", "yes", "yet", "ago", "aim", "any", "art", "ask", "box",
        "bus", "cap", "cup", "dry", "ear", "egg", "fan", "fat", "fig", "fox",
        "gas", "god", "gun", "hat", "hen", "ice", "ill", "inn", "jam", "jar",
        "jet", "joy", "key", "lab", "lid", "lip", "log", "mad", "mix", "mud",
        "net", "nut", "odd", "owl", "pan", "pat", "pet", "pie", "pig", "pin",
        "pop", "rat", "rib", "rod", "rug", "saw", "sin", "sip", "ski", "sky",
        "spy", "sum", "tag", "tan", "tap", "tea", "tip", "toe", "ton", "toy",
        "tub", "van", "vet", "web", "wig", "won", "wax", "zoo", "zip", "ale",
        "apt", "arc", "ark", "ash", "ate", "awe", "axe", "bane", "barn", "bath"
    };

    private static final String[] MEDIUM = {
        "about", "above", "after", "again", "against", "almost", "along", "already",
        "always", "among", "another", "answer", "around", "because", "before", "began",
        "begin", "being", "below", "between", "beyond", "both", "bring", "build",
        "built", "business", "called", "came", "cannot", "change", "child", "children",
        "city", "close", "come", "company", "could", "country", "course", "different",
        "does", "done", "during", "each", "early", "earth", "enough", "even", "every",
        "example", "face", "fact", "family", "feel", "feet", "find", "first", "five",
        "following", "found", "friend", "from", "gave", "give", "given", "going",
        "gone", "good", "government", "great", "group", "hand", "hard", "have", "head",
        "hear", "help", "here", "high", "hold", "home", "house", "idea", "important",
        "into", "just", "keep", "kind", "know", "land", "large", "last", "later",
        "learn", "leave", "left", "less", "letter", "life", "light", "line", "little",
        "live", "local", "long", "look", "made", "make", "many", "might", "mile",
        "mind", "money", "month", "more", "most", "mother", "move", "much", "must",
        "name", "national", "need", "never", "next", "night", "number", "often", "once",
        "only", "open", "order", "other", "over", "own", "part", "people", "place",
        "plan", "play", "point", "possible", "power", "present", "problem", "program",
        "public", "quite", "rather", "really", "right", "same", "school", "second",
        "seem", "self", "should", "side", "since", "small", "social", "something",
        "sound", "south", "special", "start", "state", "still", "story", "strong",
        "such", "system", "take", "tell", "than", "that", "their", "them", "then",
        "there", "these", "they", "thing", "think", "this", "those", "thought", "three",
        "through", "time", "today", "together", "too", "took", "true", "turn", "under",
        "until", "upon", "used", "very", "want", "water", "went", "were", "what",
        "where", "which", "while", "white", "whole", "will", "with", "within", "without",
        "woman", "women", "work", "world", "would", "write", "year", "young", "your",
        "being", "believe", "better", "black", "blood", "board", "body", "book", "carry",
        "catch", "certain", "chance", "check", "class", "clear", "color", "common",
        "consider", "continue", "control", "corner", "cost", "court", "cover", "cross",
        "death", "decide", "deep", "develop", "direct", "draw", "drive", "drop", "doubt",
        "effect", "either", "enter", "evening", "event", "except", "expect", "experience",
        "explain", "express", "field", "figure", "final", "fine", "finish", "fire", "firm",
        "floor", "force", "form", "front", "full", "game", "general", "girl", "glass",
        "gotten", "ground", "growth", "half", "happen", "heart", "history", "human",
        "hundred", "image", "include", "increase", "indeed", "instead", "interest",
        "itself", "join", "judge", "just", "kill", "kitchen", "knew", "lady", "language"
    };

    private static final String[] HARD = {
        "able", "accept", "according", "account", "across", "action", "activity",
        "actually", "address", "administration", "admit", "adult", "affect", "agency",
        "agent", "agree", "ahead", "allow", "almost", "alone", "already", "although",
        "American", "among", "amount", "analysis", "anything", "anyone", "anything",
        "appear", "apply", "approach", "area", "argue", "arm", "army", "article",
        "artist", "assume", "attack", "attention", "audience", "author", "authority",
        "available", "avoid", "away", "bank", "bar", "base", "beat", "beautiful",
        "became", "become", "bed", "before", "behind", "belief", "benefit", "beside",
        "best", "better", "beyond", "big", "billion", "black", "blue", "board", "boat",
        "body", "book", "born", "boss", "bottom", "box", "boy", "break", "bring",
        "brother", "budget", "build", "building", "business", "but", "buy", "buyer",
        "call", "camera", "campaign", "campus", "cancer", "candidate", "capital",
        "car", "card", "care", "career", "carry", "case", "catch", "category",
        "cause", "cell", "center", "central", "century", "certain", "certainly",
        "chair", "challenge", "chance", "change", "character", "charge", "check",
        "child", "choice", "choose", "church", "citizen", "civil", "claim", "class",
        "clear", "clearly", "close", "coach", "cold", "collection", "college",
        "color", "come", "commercial", "common", "community", "company", "compare",
        "computer", "concern", "condition", "conference", "Congress", "consider",
        "consumer", "contain", "continue", "control", "cost", "could", "country",
        "couple", "course", "court", "cover", "create", "crime", "cultural", "culture",
        "cup", "current", "customer", "cut", "dark", "data", "daughter", "dead",
        "deal", "death", "debate", "decade", "decide", "decision", "deep", "defense",
        "degree", "democrat", "democratic", "describe", "design", "despite", "detail",
        "determine", "develop", "development", "die", "difference", "different",
        "difficult", "dinner", "direction", "director", "discover", "discuss",
        "discussion", "disease", "doctor", "dog", "door", "down", "draw", "dream",
        "drive", "drop", "drug", "early", "east", "easy", "economic", "economy",
        "edge", "education", "effect", "effort", "eight", "either", "election",
        "else", "employee", "end", "energy", "enjoy", "enough", "enter", "entire",
        "environment", "environmental", "especially", "establish", "even", "evening",
        "event", "ever", "every", "everybody", "everyone", "everything", "evidence",
        "exactly", "example", "executive", "exist", "expect", "experience", "expert",
        "explain", "eye", "face", "fact", "factor", "fail", "fall", "family", "far",
        "fast", "father", "fear", "federal", "feel", "feeling", "few", "field",
        "fight", "figure", "fill", "film", "final", "finally", "financial", "find",
        "fine", "finger", "finish", "fire", "firm", "first", "fish", "five", "floor",
        "fly", "focus", "follow", "food", "foot", "for", "force", "foreign", "forget",
        "form", "former", "forward", "four", "free", "friend", "from", "front",
        "full", "fund", "future", "garden", "gas", "general", "generation", "get",
        "girl", "give", "glass", "go", "goal", "god", "good", "government", "great",
        "green", "ground", "group", "grow", "growth", "guess", "gun", "guy", "hair",
        "half", "hand", "hang", "happen", "happy", "hard", "have", "he", "head",
        "health", "hear", "heart", "heat", "heavy", "help", "her", "here", "herself",
        "high", "him", "himself", "his", "history", "hit", "hold", "home", "hope",
        "hospital", "hot", "hotel", "hour", "house", "how", "however", "huge", "human"
    };

    private static final String[] EXPERT = {
        "accomplish", "achievement", "acknowledge", "acquire", "adaptation",
        "administration", "advertisement", "aesthetic", "affiliate", "aggression",
        "allocation", "alternative", "ambassador", "ambition", "amendment",
        "announcement", "anonymous", "anticipate", "appreciate", "approximate",
        "arbitrary", "architecture", "assessment", "assignment", "assistance",
        "association", "assumption", "atmosphere", "attachment", "attraction",
        "availability", "background", "bankruptcy", "beautiful", "behalf",
        "beneficial", "biodiversity", "biography", "breakthrough", "brilliant",
        "bureaucracy", "calculation", "capability", "capitalism", "catastrophe",
        "celebration", "certificate", "challenge", "championship", "characteristic",
        "circumstance", "civilization", "classification", "collaboration", "colleague",
        "combination", "comfortable", "commemorate", "commencement", "commentary",
        "commercialism", "commissioner", "communication", "comparative", "compensate",
        "compensation", "competence", "competitive", "complaint", "complementary",
        "complexity", "composition", "comprehensive", "compromise", "compulsory",
        "concentration", "conceptual", "conclusion", "confederation", "conference",
        "configuration", "confirmation", "confrontation", "congressional", "conscience",
        "consciousness", "consecutive", "consequence", "conservation", "considerable",
        "consistency", "consolidation", "constellation", "constitution", "construction",
        "consultation", "consumption", "contemporary", "controversial", "convenience",
        "conventional", "conversation", "cooperation", "coordination", "corporation",
        "correspondence", "cosmopolitan", "counterproductive", "craftsman", "creativity",
        "credibility", "cryptocurrency", "cultivation", "culturally", "curriculum",
        "cybersecurity", "declaration", "decommission", "decomposition", "dedication",
        "defensive", "definition", "delegation", "deliberate", "demonstration",
        "denomination", "department", "dependence", "deployment", "depression",
        "deprivation", "description", "designer", "designated", "destination",
        "destruction", "detachment", "development", "differentiate", "dignitary",
        "diligence", "diplomatic", "disappearance", "discrimination", "displacement",
        "disposable", "dissemination", "dissertation", "distinguished", "distribution",
        "disturbance", "documentation", "domesticated", "dramatically", "dreadnought",
        "effectiveness", "elaboration", "elderly", "electorate", "electronic",
        "embarrassment", "embodiment", "emergency", "emigration", "emotional",
        "encouragement", "encyclopedia", "engagement", "enhancement", "enforcement",
        "enlightenment", "enterprise", "entertainment", "entrepreneur", "environment",
        "environmentalist", "epidemiology", "equipment", "establishment", "evaporation",
        "evolutionary", "exaggeration", "examination", "extraordinary", "fabrication",
        "facilitate", "familiarity", "fascination", "fatherland", "feasibility",
        "flexibility", "flourishing", "foreknowledge", "forthcoming", "foundation",
        "fragmentation", "framework", "franchise", "freelance", "functionality",
        "generalization", "generation", "geographical", "glamorous", "globalization",
        "government", "grandfather", "grassroots", "gravitational", "guarantee",
        "handicraft", "healthcare", "hemisphere", "hierarchical", "historical",
        "homogeneous", "hospitality", "humanitarian", "hydroelectric", "hypertension",
        "identification", "illustration", "imagination", "immigration", "imminent",
        "immunization", "implementation", "implication", "importance", "imprisonment",
        "improvement", "inaccessible", "inadequate", "inappropriate", "inauguration",
        "incorporated", "incremental", "independence", "indigenous", "indispensable",
        "individualism", "industrialization", "inevitable", "inexpensive", "infrastructure",
        "inheritance", "initiative", "innovation", "inspirational", "installation",
        "institutional", "intellectual", "intelligence", "interdependent", "interesting",
        "interference", "intergovernmental", "intermediate", "international", "interpretation",
        "interruption", "intersection", "intervention", "introduction", "investigation",
        "irresponsible", "jurisdiction", "justification", "keyboarding", "knowledgeable",
        "laboratory", "legislation", "lexicography", "linguistics", "literature",
        "maintenance", "management", "manipulation", "manufacturing", "mathematics",
        "measurement", "mechanical", "mediterranean", "membership", "methodology",
        "microprocessor", "misunderstanding", "mobilization", "modernization", "modification",
        "monopolization", "motivation", "multiculturalism", "multidimensional", "multilateral",
        "multimedia", "municipality", "nationalism", "nationalization", "negotiation",
        "neighborhood", "nevertheless", "nonprofessional", "nonproliferation", "normalization",
        "notwithstanding", "obsolescence", "obstruction", "occupational", "oceanography",
        "operational", "opportunity", "optimization", "organization", "orientation",
        "originality", "ornamentation", "outstanding", "overpopulation", "overwhelming",
        "parenthood", "parliamentary", "participation", "partitioning", "partnership",
        "performance", "personalized", "pharmaceutical", "phenomenon", "philanthropy",
        "philosophical", "photosynthesis", "pneumonia", "polynomial", "popularization",
        "possibility", "practically", "precipitation", "preoccupation", "prerogative",
        "presentation", "preservation", "presidential", "prestigious", "problematic",
        "procrastination", "professionalism", "profitability", "prognosis", "programmability",
        "progression", "prohibition", "proliferation", "pronunciation", "proportionally",
        "proposition", "proprietary", "prosecution", "prosperity", "protagonist",
        "protectionism", "psychological", "publication", "qualification", "questionable",
        "questionnaire", "randomization", "rationalization", "reaffirmation", "realization",
        "reconstruction", "redevelopment", "reestablishment", "refrigeration", "rehabilitation",
        "reimbursement", "reimposition", "reincarnation", "reinforcement", "reinterpretation",
        "reintroduction", "reinvestment", "rejuvenation", "relationship", "reorganization",
        "representation", "republicanism", "reunification", "reunification", "revolutionary",
        "rhetorical", "safeguarding", "salvation", "scattergood", "schizophrenia",
        "scholarship", "simplification", "simultaneous", "sophisticated", "specialization",
        "specification", "standardization", "strengthening", "substantiation", "superintendent",
        "sustainability", "synchronization", "telecommunication", "transformation",
        "transportation", "troubleshooting", "unconstitutional", "understanding",
        "underwater", "unemployment", "unexceptional", "unfortunately", "unprecedented",
        "unpredictable", "unprofessional", "unquestionably", "unreasonable", "unsatisfactory",
        "unsuccessful", "unsustainable", "unwillingness", "urbanization", "vaporization",
        "veterinarian", "visualization", "vulnerability", "waterproofing", "weatherization",
        "westernization", "whistleblower", "wholehearted", "withdrawal", "withstanding",
        "workforce", "workmanship", "worthwhile", "yellowhammer", "zoogeography"
    };

    private static final List<String[]> TIERS = Arrays.asList(EASY, MEDIUM, HARD, EXPERT);
    private static final Random random = new Random();

    public static String getRandomWord(int tier) {
        int index = Math.min(tier, TIERS.size() - 1);
        String[] words = TIERS.get(index);
        return words[random.nextInt(words.length)];
    }

    public static String getTierName(int tier) {
        return switch (tier) {
            case 0 -> "EASY";
            case 1 -> "MEDIUM";
            case 2 -> "HARD";
            case 3 -> "EXPERT";
            default -> "MASTER";
        };
    }

    public static int getWordCount(int tier) {
        int index = Math.min(tier, TIERS.size() - 1);
        return TIERS.get(index).length;
    }
}
