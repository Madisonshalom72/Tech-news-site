package com.example.data

import com.example.model.LiveChatMessage
import com.example.model.MediaItem
import com.example.model.MediaType

object SampleDataProvider {

    val categories = listOf(
        "All Media",
        "Live Keynotes",
        "AI & Cloud",
        "Hardware Demos",
        "Podcasts",
        "Cybersecurity",
        "Silicon & Quantum"
    )

    val featuredLiveStream = MediaItem(
        id = "live-silicon-quantum-2025",
        title = "Next-Gen Silicon & Quantum Architecture: Annual Global Developer Summit 2025",
        description = "Live coverage of breakthrough processor architectures, neural compute engines, and decentralized cloud infrastructure straight from the main stage.",
        type = MediaType.LIVE_STREAM,
        category = "Silicon & Quantum",
        duration = "LIVE",
        durationSeconds = 7200,
        date = "Streaming Live",
        viewsOrListeners = "48.2K watching",
        speakerOrHost = "Dr. Elena Vance & Satya K.",
        speakerAvatarText = "EV",
        imageUrl = "https://images.unsplash.com/photo-1540575467063-178a50c2df87?auto=format&fit=crop&q=80&w=1200",
        isLive = true,
        keyTakeaways = listOf(
            "Sub-1nm gate length achieved through ribbon-FET channel stacking.",
            "Integrated cryogenic optical interlinks directly on quantum multi-chip modules.",
            "Real-time compiler acceleration reducing neural model compilation from minutes to 450ms.",
            "Hardware-enforced zero-trust memory enclaves in consumer silicon."
        ),
        transcriptSample = "[00:14:02] Dr. Vance: 'We are demonstrating the first monolithic photonic interconnect with under 0.2 picojoules per bit transfer overhead. This fundamentally redefines data center scale-out.'",
        chapterTimestamps = listOf(
            "00:00" to "Opening Keynote & Industry State",
            "15:20" to "Ribbon-FET & Atomic Stacking Breakthrough",
            "34:10" to "Photonic Computing & Optical Interlinks",
            "52:45" to "Cryogenic Quantum Hardware Demo",
            "01:10:00" to "Live Developer Q&A"
        )
    )

    val allMediaItems: List<MediaItem> = listOf(
        featuredLiveStream,
        MediaItem(
            id = "demo-neural-robotics",
            title = "Autonomous Neural Robotics & Spatial Navigation Engine",
            description = "A deep-dive engineering breakdown of edge-compute vision transformers operating entirely offline on localized hardware.",
            type = MediaType.KEYNOTE_DEMO,
            category = "Hardware Demos",
            duration = "18:45",
            durationSeconds = 1125,
            date = "Nov 18, 2025",
            viewsOrListeners = "12.4K views",
            speakerOrHost = "Dr. Elena Vance",
            speakerAvatarText = "DR",
            imageUrl = "https://images.unsplash.com/photo-1485827404703-89b55fcc595e?auto=format&fit=crop&q=80&w=800",
            keyTakeaways = listOf(
                "Vision transformer pruned to 85MB running at 120 FPS on edge silicon.",
                "Zero cloud dependency: sub-millisecond LiDAR and stereo depth fusion.",
                "Reinforcement learning policies generalized across uneven physical terrains."
            ),
            transcriptSample = "[04:22] Elena: 'Notice how the robot calculates slip coefficients dynamically before the motor controller commits foot placement torque.'"
        ),
        MediaItem(
            id = "demo-edge-llms",
            title = "Foundation Models at the Edge: Optimizing LLMs for Mobile",
            description = "Exploring quantized weights, hardware acceleration pipelines, and sub-10ms inference benchmarks on consumer devices.",
            type = MediaType.KEYNOTE_DEMO,
            category = "AI & Cloud",
            duration = "32:10",
            durationSeconds = 1930,
            date = "Nov 17, 2025",
            viewsOrListeners = "34.1K views",
            speakerOrHost = "Marcus Kincaid",
            speakerAvatarText = "MK",
            imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&q=80&w=800",
            keyTakeaways = listOf(
                "3-bit mixed precision quantization preserving 99.1% MMLU accuracy.",
                "NPU direct memory access bypassing thermal throttling limits.",
                "Streaming KV-cache compression for 128k context windows on 12GB RAM."
            ),
            transcriptSample = "[12:15] Marcus: 'By mapping attention heads to matrix execution units in cache, we avoid memory bandwidth bottlenecks.'"
        ),
        MediaItem(
            id = "demo-post-quantum-crypto",
            title = "Post-Quantum Cryptography: Preparing Infrastructure for 2030",
            description = "A technical walkthrough of lattice-based encryption standards and migration strategies for enterprise cloud architectures.",
            type = MediaType.KEYNOTE_DEMO,
            category = "Cybersecurity",
            duration = "24:05",
            durationSeconds = 1445,
            date = "Nov 16, 2025",
            viewsOrListeners = "19.8K views",
            speakerOrHost = "Aisha Sharma",
            speakerAvatarText = "AS",
            imageUrl = "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?auto=format&fit=crop&q=80&w=800",
            keyTakeaways = listOf(
                "ML-KEM and ML-DSA algorithm implementation best practices.",
                "Hybrid TLS handshakes combining classical elliptic curves with lattice keys.",
                "Automated crypto-agility auditing for legacy database schemas."
            ),
            transcriptSample = "[08:40] Aisha: 'If an adversary is harvesting encrypted traffic today, post-quantum migration must happen before harvest-now-decrypt-later matures.'"
        ),
        MediaItem(
            id = "demo-orbital-compute",
            title = "Kubernetes at Orbital Scale: Low-Earth-Orbit Satellite Compute Nodes",
            description = "How constellations of micro-satellites form federated container clusters with intermittent inter-satellite laser links.",
            type = MediaType.KEYNOTE_DEMO,
            category = "AI & Cloud",
            duration = "21:15",
            durationSeconds = 1275,
            date = "Nov 14, 2025",
            viewsOrListeners = "15.2K views",
            speakerOrHost = "Sarah Jenkins",
            speakerAvatarText = "SJ",
            imageUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&q=80&w=800",
            keyTakeaways = listOf(
                "Delay-tolerant consensus protocols overcoming 8-minute line-of-sight outages.",
                "Radiation-hardened ARM nodes executing real-time atmospheric sensor inference.",
                "Direct downlink prioritization using edge inference telemetry."
            ),
            transcriptSample = "[10:05] Sarah: 'Each satellite node operates an autonomous control plane that gracefully rejoins the cluster as laser crosslinks lock.'"
        ),
        MediaItem(
            id = "podcast-silicon-horizon",
            title = "The Silicon Horizon - Ep 84: Inside the 2nm GAAFET Transistor Revolution",
            description = "An investigative discussion with principal lithography architects on extreme ultraviolet (High-NA EUV) optics and pellicle materials.",
            type = MediaType.PODCAST,
            category = "Podcasts",
            duration = "54:20",
            durationSeconds = 3260,
            date = "Nov 18, 2025",
            viewsOrListeners = "62.4K listens",
            speakerOrHost = "Dr. Ken Thorne & Lisa Wang",
            speakerAvatarText = "KT",
            imageUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&q=80&w=800",
            keyTakeaways = listOf(
                "High-NA 0.55 numerical aperture optical assemblies.",
                "Backside power delivery networks (BSPDN) reducing IR drop by 30%.",
                "Yield challenges in multi-layer carbon nanotube interconnects."
            ),
            transcriptSample = "[18:30] Ken: 'When you move the power delivery rails underneath the silicon substrate, you free up critical wiring layers on top.'"
        ),
        MediaItem(
            id = "podcast-decentralized-future",
            title = "Decentralized Future - Ep 42: Edge AI Agents and Autonomous Economic Swarms",
            description = "Exploring multi-agent negotiation frameworks, decentralized identity verification, and verifiable execution proofs.",
            type = MediaType.PODCAST,
            category = "Podcasts",
            duration = "48:15",
            durationSeconds = 2895,
            date = "Nov 16, 2025",
            viewsOrListeners = "41.8K listens",
            speakerOrHost = "Alex Rivera",
            speakerAvatarText = "AR",
            imageUrl = "https://images.unsplash.com/photo-1639762681485-074b7f938ba0?auto=format&fit=crop&q=80&w=800",
            keyTakeaways = listOf(
                "Zero-knowledge proofs validating model weight integrity without disclosure.",
                "Micro-transaction settlement for compute spot auctions between local agents.",
                "Agentic safety guardrails in unregulated multi-party barter protocols."
            ),
            transcriptSample = "[22:04] Alex: 'Autonomous agents need cryptographic reputations rather than centralized credit scores.'"
        ),
        MediaItem(
            id = "podcast-kernel-cloud",
            title = "Kernel & Cloud - Ep 119: Memory Safety in Modern Operating Systems",
            description = "Kernel maintainers discuss the reality of Rust integration in production OS kernels, driver ABI stability, and concurrency primitives.",
            type = MediaType.PODCAST,
            category = "Podcasts",
            duration = "38:50",
            durationSeconds = 2330,
            date = "Nov 12, 2025",
            viewsOrListeners = "89.1K listens",
            speakerOrHost = "Linus T. & Greg K.",
            speakerAvatarText = "LT",
            imageUrl = "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?auto=format&fit=crop&q=80&w=800",
            keyTakeaways = listOf(
                "Asynchronous driver abstractions without dynamic heap allocations.",
                "Formal verification tools detecting lock order inversions in preemption kernels.",
                "Zero-overhead abstraction benchmarks across x86-64 and RISC-V."
            ),
            transcriptSample = "[09:12] Greg: 'Eliminating whole classes of use-after-free vulnerabilities in device drivers is worth the compiler complexity.'"
        ),
        MediaItem(
            id = "podcast-quantum-leap",
            title = "Quantum Leap - Ep 15: Error Mitigation vs Fault Tolerant Logical Qubits",
            description = "Comparing surface codes, color codes, and topological quantum computing architectures for commercial advantage.",
            type = MediaType.PODCAST,
            category = "Podcasts",
            duration = "61:10",
            durationSeconds = 3670,
            date = "Nov 10, 2025",
            viewsOrListeners = "33.7K listens",
            speakerOrHost = "Prof. Maya Patel",
            speakerAvatarText = "MP",
            imageUrl = "https://images.unsplash.com/photo-1635070041078-e363dbe005cb?auto=format&fit=crop&q=80&w=800",
            keyTakeaways = listOf(
                "Magic state distillation overheads in surface code architectures.",
                "Neutral atom shuttling arrays achieving 1,000+ coherent physical qubits.",
                "Algorithmic benchmarks for quantum chemistry molecular simulation."
            ),
            transcriptSample = "[31:50] Maya: 'The tipping point isn't physical qubit counts—it's the physical-to-logical qubit ratio reaching under 100:1.'"
        )
    )

    val liveChatMessages: List<LiveChatMessage> = listOf(
        LiveChatMessage("m1", "Devon_K", 0xFF005BFF, "The latency benchmarks look incredible! 🔥", 2),
        LiveChatMessage("m2", "PriyaTech", 0xFF00894B, "Is this running on 3nm or the experimental 1.8nm GAA?", 5),
        LiveChatMessage("m3", "Alex_V", 0xFF9C27B0, "Dr. Vance is dropping serious compiler architecture gems here.", 9),
        LiveChatMessage("m4", "QuantumCode", 0xFFE91E63, "Photonic optical interlinks eliminate so much heat dissipation.", 14),
        LiveChatMessage("m5", "SiliconNinja", 0xFFFF9800, "Sub-10ms edge inference confirmed on consumer chips!", 18),
        LiveChatMessage("m6", "HardwareGeek", 0xFF00BCD4, "Saved this keynote to watch the cryogenic demo again later.", 24),
        LiveChatMessage("m7", "Maya_AI", 0xFF4CAF50, "Are slides and Whitepaper links available in the feed?", 29),
        LiveChatMessage("m8", "CloudArchitect", 0xFF3F51B5, "The decentralized satellite Kubernetes demo was wild! 🛰️", 35)
    )

    val intelligenceFeedAlerts = listOf(
        "BREAKING: 1.8nm ribbon-FET test silicon achieves 22% clock frequency uplift with 30% lower thermal dissipation.",
        "STANDARDS: NIST finalizes primary post-quantum cipher suites for zero-trust cloud transports.",
        "RELEASE: Open-source local vision transformer inference library reaches version 2.0 with sub-8ms latency.",
        "EVENT: Keynote Q&A session opens in 15 minutes. Submit technical questions via the stream tab."
    )
}
