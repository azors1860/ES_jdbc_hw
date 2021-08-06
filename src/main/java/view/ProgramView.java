package view;

public class ProgramView {
    private final Console view = new Console();
    private final SkillView skillView = new SkillView();
    private final TeamView teamView = new TeamView();
    private final DeveloperView developerView = new DeveloperView();


    public void runProgram() {
        String message =
                "1 : Навыки\n" +
                        "2 : Разработчики\n" +
                        "3 : Команды\n" +
                        "-1 : Завершить программу\n";
        while (true) {
            view.printMessage(message);
            int input = view.inputInt();
            switch (input) {
                case 1:
                    skillView.skillMenu();
                    break;
                case 2:
                    developerView.developerMenu();
                    break;
                case 3:
                    teamView.teamMenu();
                    break;
                case -1:
                    System.exit(0);
                default:
                    view.messageIncorrectInput();
            }
        }
    }
}

