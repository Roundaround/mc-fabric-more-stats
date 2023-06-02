plugins {
  id("roundalib") version "0.3.6"
}

loom {
  accessWidenerPath.set(file("src/main/resources/morestats.accesswidener"))
}
