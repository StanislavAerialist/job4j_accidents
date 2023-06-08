package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidents")
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String text;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", foreignKey = @ForeignKey(name = "TYPES_ID_FKEY"))
    private AccidentType type;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "accidents_rules", joinColumns = {
            @JoinColumn(name = "accident_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "rule_id", nullable = false, updatable = false)})
    private Set<Rule> rules = new HashSet<>();

    public void addRule(Rule rule) {
        rules.add(rule);
    }
}