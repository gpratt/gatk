package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline._
import java.io.File
import org.broadinstitute.sting.queue.function.CommandLineFunction

class TrimGalore extends CommandLineFunction {
  //see arguments on "trim_galore -h" command line for more documentation

  @Input(doc = "input fastq file", shortName = "inFastq", fullName = "input_fastq_file", required = true)
  var inFastq: File = _

  @Input(doc = "input fastq paired file", shortName = "inFastqPair", fullName = "input_fastq_pair_file",
    required = false)
  var inFastqPair: File = _
  
  @Argument(doc="If paired end", shortName="paired", fullName="paired", required=true)
  var paired: Boolean = _

  @Argument(doc = "Stats report for trim_galore", shortName = "report", fullName = "report", required = true)
  var report: String = _

  @Argument(doc = "Adapters to trim (-a/--adapater for trim_galore)", shortName = "adapter", 
  	fullName = "adapter", required = false)
  var adapter: List[String] = Nil

  @Argument(doc = "Overlapping minimum (--stringency for trim_galore)", 
  	shortName = "overlap", fullName = "overlap", required = false)
  var stringency: Int = _

  @Argument(doc = "error rate", shortName = "error_rate", fullName = "error_rate", required = false)
  var error_rate: Int = _

  @Argument(doc = "Minimum read length", shortName = "minimum_length", 
  	fullName = "minimum_length", required = false)
  var minimum_length: Int = _

  @Argument(doc = "quality_cutoff", shortName = "quality_cutoff", fullName = "quality_cutoff", required = false)
  var quality_cutoff: Int = _

  override def shortDescription = "trim_galore"
  //Option[Int] 
  this.wallTime = Option((8 * 60 * 60).toLong)

  def commandLine = "trim_galore --dont_gzip" +
    optional("-e", error_rate) +
    optional("--stringency", stringency) +
    optional("--quality", quality_cutoff) +
    optional("--minimum-length", minimum_length) +
    repeat("--adapter", adapter) +
    conditional(paired, "--paired")
    conditional(paired, repeat("--adapter2", adapter))
    required(inFastq) + 
    optional(inFastqPair)
  this.isIntermediate = false
}