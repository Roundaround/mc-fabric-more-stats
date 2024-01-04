plugins {
  id("roundalib") version "0.4.0"
}

loom {
  accessWidenerPath.set(file("src/main/resources/morestats.accesswidener"))
}
