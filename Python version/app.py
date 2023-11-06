from itertools import combinations

class Stable:
    skills_jorge = ["C", "C++", "C#"]
    skills_mario = ["Javascript", "CSS", "HTML", "Java"]
    skills_martin = ["Python", "R", "C", "SQL"]
    skills_lorena = ["C", "C++", "SQL", "Java", "Scala"]

    position_microsoft = ["C", "C++"]
    position_apple = ["Python", "R", "Julia", "Ruby"]
    position_ibm = ["Java", "Javascript", "SQL"]
    position_adobe = ["Scala", "Perl"]

    employees = ["jorge", "mario", "martin", "lorena"]
    employers = ["microsoft", "apple", "ibm", "adobe"]

    employees_prefer = {e: [] for e in employees}
    employers_prefer = {e: [] for e in employers}

    @staticmethod
    def intersection(list1, list2):
        return list(set(list1) & set(list2))

    @staticmethod
    def add_skill(new_skill):
        for skills_list in [Stable.skills_jorge, Stable.skills_mario, Stable.skills_martin, Stable.skills_lorena]:
            if new_skill not in skills_list:
                skills_list.append(new_skill)

    @staticmethod
    def happiness_index(matches):
        accumulator = 0
        for employer, candidate in matches.items():
            employer_pref = Stable.employers_prefer[employer]
            candidate_pref = Stable.employees_prefer[candidate]
            accumulator += employer_pref.index(candidate) + candidate_pref.index(employer)
        return accumulator

    @staticmethod
    def compute():
        employees_skills = [
            Stable.skills_jorge, Stable.skills_mario,
            Stable.skills_martin, Stable.skills_lorena
        ]
        positions_skills = [
            Stable.position_microsoft, Stable.position_apple,
            Stable.position_ibm, Stable.position_adobe
        ]

        for employee, skills in zip(Stable.employees, employees_skills):
            cell = {employer: len(Stable.intersection(skills, position))
                    for employer, position in zip(Stable.employers, positions_skills)}
            sorted_preferences = sorted(cell, key=cell.get, reverse=True)
            Stable.employees_prefer[employee] = sorted_preferences

    @staticmethod
    def compute2():
        employees_skills = [
            Stable.skills_jorge, Stable.skills_mario,
            Stable.skills_martin, Stable.skills_lorena
        ]
        positions_skills = [
            Stable.position_microsoft, Stable.position_apple,
            Stable.position_ibm, Stable.position_adobe
        ]

        for employer, position in zip(Stable.employers, positions_skills):
            cell = {employee: len(Stable.intersection(position, skills))
                    for employee, skills in zip(Stable.employees, employees_skills)}
            sorted_preferences = sorted(cell, key=cell.get, reverse=True)
            Stable.employers_prefer[employer] = sorted_preferences

    @staticmethod
    def gale_shapley():
        free_employees = set(Stable.employees)
        engaged_to = {}
        while free_employees:
            for employee in list(free_employees):
                for employer in Stable.employees_prefer[employee]:
                    if employer not in engaged_to:
                        engaged_to[employer] = employee
                        free_employees.remove(employee)
                        break
                    else:
                        current_employee = engaged_to[employer]
                        if Stable.employers_prefer[employer].index(employee) < Stable.employers_prefer[employer].index(current_employee):
                            engaged_to[employer] = employee
                            free_employees.remove(employee)
                            free_employees.add(current_employee)
                            break
        return engaged_to

    @staticmethod
    def combination(elements, k):
        return list(combinations(elements, k))

    @staticmethod
    def print_combination(combination):
        print(combination)

    @staticmethod
    def iterate(skillsandco):
        # Example logic for file writing (you should replace 'myfile.txt' with the actual file path)
        with open('myfile.txt', 'a') as file:
            for skill_combo in skillsandco:
                for skill in skill_combo:
                    file.write(skill + "\n")

# Example of using the class to perform operations
if __name__ == "__main__":
    Stable.compute()
    Stable.compute2()
    matches = Stable.gale_shapley()
    print("Matches:", matches)
    print("Happiness Index:", Stable.happiness_index(matches))
    
    Stable.add_skill("Julia")
    Stable.compute()
    Stable.compute2()
    matches = Stable.gale_shapley()
    print("Matches:", matches)
    print("Happiness Index:", Stable.happiness_index(matches))  