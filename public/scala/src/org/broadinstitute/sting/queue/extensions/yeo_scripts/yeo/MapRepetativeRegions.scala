package org.broadinstitute.sting.queue.extensions.yeo

import org.broadinstitute.sting.commandline.Output
import java.io.File
import org.broadinstitute.sting.commandline._
import org.broadinstitute.sting.queue.function.CommandLineFunction

class MapRepetitiveRegions extends CommandLineFunction {
  override def shortDescription = "FilterRepetitiveRegions"

  @Input(doc="input fastq file", shortName = "inFastq", fullName = "input_fastq_file", 
    required = true) 
  var inFastq: File = _

  @Input(doc="input fastq file paired end read", shortName = "inFastqPair", 
    fullName = "input_fastq_pair_file", required = true) 
  var inFastqPair: File = _

  @Argument(doc="Paired-end", shortName="paired", fullName="paired", required=true)
  var paired: Boolean = _

  @Output(doc="Mapped file for reads that got removed", shortName = "outRepetitive", 
    fullName = "out_repetitive", required = true) 
  var outRep: File = _
 
  @Output(doc="fastq file with repetive elements removed, with a % for the read number, e.g. sample_A_R%.fastq", 
    shortName = "outNoRepetitive", fullName = "out_no_repetitive", required = true) 
  var outNoRepetitive: File = _

  this.wallTime = Option((4 * 60 * 60).toLong)
  this.nCoresRequest = Option(16) 
  def commandLine = "bowtie2 -q -p 16 -L 20 --local --no-unal --un " + 
    conditional(paired, "--un-conc " + outNoRepetitive)
    "%s all_ref %s"
    " | samtools view -F 4 -Sb - > %s".format(outNoRep, inFastq, outRep)

}