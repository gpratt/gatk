package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class ClipAnalysis(@Input inBam: File, @Input inBed: File, @Argument species: String, @Output metrics: File,
      		   @Argument AS_Structure: String, @Argument genome_location: String,
		   @Argument phastcons_location: String, @Argument gff_db: String) extends CommandLineFunction {

  override def shortDescription = "CLIP_Analysis"
 this.wallTime = Option((4 * 60 * 60).toLong)
  var as_location = AS_Structure

  if(as_location == "none") {
    as_location = null
    println("setting location")
    println(inBam)
  }
  

  println(as_location)
  def commandLine = "clip_analysis " +
    required("--clusters", inBed) +
    required("-s", species) +
    required("--bam", inBam) +
    optional("--AS_Structure", as_location) +
    required("--genome_location", genome_location) +
    required("--phastcons_location", phastcons_location) +
    required("--nrand", 3) +
    required("--runPhast") +
    required("--metrics", metrics) +
    required("--gff_db", gff_db) 


}
