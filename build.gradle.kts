plugins {
  id("roundalib-gradle") version "1.0.0"
}

loom {
  accessWidenerPath.set(file("src/main/resources/morestats.accesswidener"))
}
