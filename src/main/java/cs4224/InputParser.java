package cs4224;

import org.apache.commons.cli.*;

// Reference: https://lightrun.com/java/java-tutorial-java-command-line-arguments/
public class InputParser {
    private Options options = new Options();
    private DefaultParser parser = new DefaultParser();
    private HelpFormatter helpFormatter = new HelpFormatter();

    /*
        https://www.cockroachlabs.com/docs/v21.1/build-a-java-app-with-cockroachdb?filters=local
        DataSource init options:

     */
    public InputParser() {
        addOption("t", "task", "Type of task: transaction or dbstate", true, true);
        addOption("d", "Database", "Database name", true, true);
        addOption("i", "ip", "IP address of CockroachDB cluster", true, false);
        addOption("p", "port", "Port of CockroachDB cluster", true, false);
        addOption("pw", "password", "Password of CockroachDB user provided", true, true);
        addOption("u", "user", "Username of CockroachDB user", true, true);
        addOption("f", "fileName", "Name of transaction query file", true, false);
        addOption("l", "logFileName", "Name of log file", true, false);
    }

    private void addOption(String opt, String longOpt, String description, boolean hasArg, boolean isRequired) {
        Option option = new Option(opt, longOpt, hasArg, description);
        option.setRequired(isRequired);
        options.addOption(option);
    }

    public CommandLine parse(String[] args) {
        try {
            final CommandLine cli = parser.parse(options, args);
            if (cli.getOptionValue("t").equalsIgnoreCase("transaction") && !cli.hasOption("f")) {
                throw new MissingArgumentException("Missing argument for option: fileName");
            }
            return cli;
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            helpFormatter.printHelp("Main", options);
            return null;
        }
    }
}
