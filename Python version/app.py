# -*- coding: utf-8 -*-
"""
Jorge Martinez-Gil, Bernhard Freudenthaler: Optimal Selection of Training Courses for Unemployed People based on Stable Marriage Model. iiWAS 2019: 260-266

@author: Jorge Martinez-Gil
"""

from itertools import combinations

class Stable:
    # Defining skills for each employee
    skills_jorge = ["C", "C++", "C#"]
    skills_mario = ["Javascript", "CSS", "HTML", "Java"]
    skills_martin = ["Python", "R", "C", "SQL"]
    skills_lorena = ["C", "C++", "SQL", "Java", "Scala"]

    # Defining skill requirements for each employer
    position_microsoft = ["C", "C++"]
    position_apple = ["Python", "R", "Julia", "Ruby"]
    position_ibm = ["Java", "Javascript", "SQL"]
    position_adobe = ["Scala", "Perl"]

    # List of employees and employers
    employees = ["jorge", "mario", "martin", "lorena"]
    employers = ["microsoft", "apple", "ibm", "adobe"]

    # Initializing preference lists for employees and employers
    employees_prefer = {e: [] for e in employees}
    employers_prefer = {e: [] for e in employers}

    @staticmethod
    def intersection(list1, list2):
        # Function to find common elements between two lists
        return list(set(list1) & set(list2))

    @staticmethod
    def add_skill(new_skill):
        # Function to add a new skill to all employees if not already present
        for skills_list in [Stable.skills_jorge, Stable.skills_mario, Stable.skills_martin, Stable.skills_lorena]:
            if new_skill not in skills_list:
                skills_list.append(new_skill)

    @staticmethod
    def happiness_index(matches):
        # Function to calculate happiness index based on matches
        accumulator = 0
        for employer, candidate in matches.items():
            employer_pref = Stable.employers_prefer[employer]
            candidate_pref = Stable.employees_prefer[candidate]
            accumulator += employer_pref.index(candidate) + candidate_pref.index(employer)
        return accumulator

    @staticmethod
    def compute():
        # Function to compute employee preferences
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
        # Function to compute employer preferences
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
        # Gale-Shapley algorithm implementation for stable matching
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
        # Function to generate combinations of a given size from a list
        return list(combinations(elements, k))

    @staticmethod
    def print_combination(combination):
        # Function to print a combination
        print(combination)

    @staticmethod
    def iterate(skillsandco):
        # Function to iterate over skills combinations and write to a file
        # Replace 'myfile.txt' with the actual file path
        with open('myfile.txt', 'a') as file:
            for skill_combo in skillsandco:
                for skill in skill_combo:
                    file.write(skill + "\n")

# Main block to execute class functions
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
 